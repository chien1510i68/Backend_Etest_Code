package com.example.ttcn2etest.mocktest.exam.service;

import com.example.ttcn2etest.mocktest.answer.entity.Answer;
import com.example.ttcn2etest.mocktest.answer.repository.AnswerRepository;
import com.example.ttcn2etest.mocktest.exam.dto.DetailExamDTO;
import com.example.ttcn2etest.mocktest.exam.dto.ExamDTO;
import com.example.ttcn2etest.mocktest.exam.entity.Exam;
import com.example.ttcn2etest.mocktest.exam.repository.ExamRepository;
import com.example.ttcn2etest.mocktest.exam.request.ExamRequest;
import com.example.ttcn2etest.mocktest.question.entity.Question;
import com.example.ttcn2etest.mocktest.question.repository.QuestionRepository;
import com.example.ttcn2etest.mocktest.section.entity.Section;
import com.example.ttcn2etest.mocktest.section.repository.SectionRepository;
import com.example.ttcn2etest.mocktest.section.request.SectionRequest;
import com.example.ttcn2etest.mocktest.section.service.SectionService;
import com.example.ttcn2etest.mocktest.user_exam.entity.UserResponse;
import com.example.ttcn2etest.mocktest.user_exam.repository.UserResponseRepository;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.StringUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ExamServiceImplm implements ExamService {

    private final SectionService sectionService;
    private final ExamRepository examRepository;
    private final SectionRepository sectionRepository;
    //    private final ModalMapper mapper ;
    private final ModelMapper mapper;
    private final UserResponseRepository userResponseRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;


    @Override
    public Exam createExam(ExamRequest request) {
        Exam exam = new Exam();
        exam.setName(request.getName());
        exam.setTimeExam(request.getTimeExam());
        examRepository.save(exam);
        List<Section> sections = new ArrayList<>();
        for (SectionRequest sectionRequest : request.getSectionRequests()) {
            Section section = sectionService.createSectionInExam(sectionRequest);
            section.setExam(exam);
            sectionRepository.save(section);
            sections.add(section);
        }
        exam.setSections(sections);
        return exam;
    }

    @Override
    public Exam updateExam(ExamRequest request) {
        Optional<Exam> exam = examRepository.findById(request.getId());
        if (!exam.isPresent()) {
            throw new RuntimeException("Không tìm thấy id");
        }
        exam.get().setTimeExam(request.getTimeExam());
        exam.get().setName(request.getName());
        examRepository.save(exam.get());
        return exam.get();
    }

    @Override
    public boolean deleteExam(long id) {
        Optional<Exam> exam = examRepository.findById(id);
        if (!exam.isPresent()) {
            throw new RuntimeException("ID không tồn tại");
        }
        List<Section> sections = sectionRepository.findSectionsByExam(exam.get());
        for (Section section : sections) {
            sectionService.deleteSection(section.getId());
        }
        for (UserResponse userResponse : exam.get().getUser_exam()) {
            userResponse.setExam(null);
            userResponseRepository.save(userResponse);

        }
        examRepository.delete(exam.get());
//        sectionRepository.deleteAll(sections);
        return true;
    }

    @Override
    public List<ExamDTO> getAllExam() {
        List<ExamDTO> examDTOS = new ArrayList<>();
        List<Exam> exams = examRepository.findAll();
        for (Exam exam : exams) {
            examDTOS.add(mapper.map(exam, ExamDTO.class));
        }
        return examDTOS;
    }

    @Override
    public DetailExamDTO getByID(long id) {
        Optional<Exam> exam = examRepository.findById(id);
        if (!exam.isPresent()) {
            throw new RuntimeException("ID không tồn tại");
        }
        return mapper.map(exam.get(), DetailExamDTO.class);
    }

    @Override
    public List<DetailExamDTO> listDetailExam() {
        List<DetailExamDTO> detailExamDTOS = new ArrayList<>();
        List<Exam> exams = examRepository.findAll();
        for (Exam exam : exams) {
            detailExamDTOS.add(mapper.map(exam, DetailExamDTO.class));
        }
        return detailExamDTOS;
    }

    @Override
    public ExamDTO addSectionToExam(SectionRequest sectionRequest) {
        Exam exam = examRepository.findExamById(sectionRequest.getExam_id());
        Section section = sectionService.createSection(sectionRequest);
        section.setExam(exam);
        sectionRepository.save(section);
        List<Section> sections = exam.getSections();
        sections.add(section);
        exam.setSections(sections);
        examRepository.save(exam);


        return mapper.map(exam, ExamDTO.class);
    }

    @Override
    public List<Section> findQuestionByType(long id, String type) {
        Optional<Exam> exam = examRepository.findById(id);
        return exam.get().getSections().stream().filter((section -> section.getType().equals(type))).collect(Collectors.toList());

    }

    @Override
    public Exam createExamByExcel(String path) {
/*
        try {
            Exam exam = new Exam();
            boolean isNewSection = false;
            FileInputStream file = new FileInputStream(new File(path));
            Workbook workbook = new XSSFWorkbook(file);

            examRepository.save(exam);
            String currentExamName = null;


            boolean isExamNameRead = false;


            String currentNameSection = null;
            Question currentQuestion = null;


            Sheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.iterator();
            rowIterator.next();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Section sectionCurrent = null;

                Cell examNameCell = row.getCell(0);
                Cell timeExam = row.getCell(1);
                if (!isExamNameRead) {
                    exam.setName(examNameCell.getStringCellValue());
                    exam.setTimeExam(timeExam.getColumnIndex());
                    currentExamName = examNameCell.getStringCellValue();
                    isExamNameRead = true;
                }
//
                Cell sectionNameCell = row.getCell(2);
                Cell descriptionSectionCell = row.getCell(3);
                Cell fileSectionCell = row.getCell(4);
                Cell typeSectionCell = row.getCell(5);
                Cell questionContentCell = row.getCell(6);
                Cell questionTypeCell = row.getCell(7);
                Cell answer1Cell = row.getCell(8);
                Cell answer2Cell = row.getCell(9);
                Cell answer3Cell = row.getCell(10);
                Cell answer4Cell = row.getCell(11);
                Cell answer5Cell = row.getCell(12);
                Cell answer6Cell = row.getCell(13);
                Cell fileQuestionCell = row.getCell(14);
                Cell descriptionQuestionCell = row.getCell(15);
                Cell correctAnswersCell = row.getCell(16);
                Cell pointCell = row.getCell(17);


                String sectionName = (sectionNameCell != null) ? row.getCell(2).getStringCellValue() : null;
                String descriptionSection = (descriptionSectionCell != null) ? descriptionSectionCell.getStringCellValue() : null;
                String fileSection = (fileSectionCell != null) ? fileSectionCell.getStringCellValue() : null;
                String typeSection = (typeSectionCell != null) ? typeSectionCell.getStringCellValue() : null;
                String questionContent = questionContentCell.getStringCellValue();
                String questionType = questionTypeCell.getStringCellValue();
                String answer1 = answer1Cell.getStringCellValue();
                String answer2 = answer2Cell.getStringCellValue();
                String answer3 = answer3Cell.getStringCellValue();
                String answer4 = answer4Cell.getStringCellValue();
                String answer5 = (answer5Cell != null) ? answer5Cell.getStringCellValue() : null;
                String answer6 = (answer6Cell != null) ? answer6Cell.getStringCellValue() : null;
                String descriptionQuestion = (descriptionQuestionCell != null) ? descriptionQuestionCell.getStringCellValue() : null;
                Float point = pointCell.getRow().getHeightInPoints();
                String correctAnswers = correctAnswersCell.getStringCellValue();
//                if (!examName.equals(currentExamName)) {
//                    currentExamName = examName;
//                    exam.setName(currentExamName);
//                }
                if (sectionNameCell != null && !sectionName.equals(currentNameSection)) {
                    currentNameSection = sectionName;
                    log.info("Tên section khác la : {}", fileSection);
//                    Section section = new Section();
                    sectionCurrent = Section.builder()
                            .title(sectionName)
                            .questions(new ArrayList<>())
                            .type(typeSection)
                            .file(fileSection)
                            .description(descriptionSection)
                            .exam(exam).build();
                    sectionRepository.save(sectionCurrent);

                    String[] correctAnswersArray = correctAnswers.split(",");

                    List<Integer> correctAnswersList = new ArrayList<>();
                    for (String answer : correctAnswersArray) {
                        try {
                            int value = Integer.parseInt(answer.trim());
                            correctAnswersList.add(value);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                    Question question = Question.builder().content(questionContent)
                            .section(sectionCurrent)
                            .questionType(questionType)
                            .point(point)
                            .description(descriptionQuestion)
                            .choiceCorrect(correctAnswersList).build();

                    List<Answer> answers = new ArrayList<>();
                    answers.add(new Answer(answer1, 0, question));
                    answers.add(new Answer(answer2, 1, question));
                    answers.add(new Answer(answer3, 2, question));
                    answers.add(new Answer(answer4, 3, question));
                    if (StringUtils.hasText(answer5)) {
                        answers.add(new Answer(answer5, 4, question));
                    }
                    if (StringUtils.hasText(answer6)) {
                        answers.add(new Answer(answer6, 5, question));
                    }
                    question.setListAnswer(answers);
                    questionRepository.save(question);
                    List<Question> questionList = sectionCurrent.getQuestions();
                    questionList.add(question);
                    sectionCurrent.setQuestions(questionList);
                    isNewSection = true ;


                } else if (!sectionName.equals(currentNameSection) && sectionCurrent != null && isNewSection) {

                    String[] correctAnswersArray = correctAnswers.split(",");

                    List<Integer> correctAnswersList = new ArrayList<>();
                    for (String answer : correctAnswersArray) {
                        try {
                            int value = Integer.parseInt(answer.trim());
                            correctAnswersList.add(value);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                    Question question = Question.builder().content(questionContent)
                            .section(sectionCurrent)
                            .questionType(questionType)
                            .point(point)
                            .description(descriptionQuestion)
                            .choiceCorrect(correctAnswersList).build();
                    List<Answer> answers = new ArrayList<>();
                    answers.add(new Answer(answer1, 0, question));
                    answers.add(new Answer(answer2, 1, question));
                    answers.add(new Answer(answer3, 2, question));
                    answers.add(new Answer(answer4, 3, question));
                    if (StringUtils.hasText(answer5)) {
                        answers.add(new Answer(answer5, 4, question));
                    }
                    if (StringUtils.hasText(answer6)) {
                        answers.add(new Answer(answer6, 5, question));
                    }
                    question.setListAnswer(answers);
                    questionRepository.save(question);
                    List<Question> questionList = sectionCurrent.getQuestions();
                    questionList.add(question);
                    sectionCurrent.setQuestions(questionList);
                    sectionRepository.save(sectionCurrent);


                }


                log.info("Thành công {}", exam.getName());
            }


            file.close();
            workbook.close();
            return exam;

        } catch (IOException e) {
            e.printStackTrace();
        }


*/

        try {
            Exam exam = new Exam();
            FileInputStream file = new FileInputStream(new File(path));
            Workbook workbook = new XSSFWorkbook(file);

            examRepository.save(exam);
            String currentExamName = null;


            boolean isExamNameRead = false;


            String currentNameSection = null;
            Section currentSection = null;


            Sheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.iterator();
            rowIterator.next();
            Section sectionCurrent = null;

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();


                Cell examNameCell = row.getCell(0);
                Cell typeExam = row.getCell(1);
                Cell timeExam = row.getCell(2);
                if (!isExamNameRead) {
                    exam.setName(examNameCell.getStringCellValue());
                    exam.setType(typeExam.getStringCellValue());
                    exam.setTimeExam((long)timeExam.getNumericCellValue());
                    currentExamName = examNameCell.getStringCellValue();
                    isExamNameRead = true;
                }
//
                Cell sectionNameCell = row.getCell(3);
                Cell descriptionSectionCell = row.getCell(4);
                Cell fileSectionCell = row.getCell(5);
                Cell typeSectionCell = row.getCell(6);
                Cell questionContentCell = row.getCell(7);
                Cell questionTypeCell = row.getCell(8);
                Cell answer1Cell = row.getCell(9);
                Cell answer2Cell = row.getCell(10);
                Cell answer3Cell = row.getCell(11);
                Cell answer4Cell = row.getCell(12);
                Cell answer5Cell = row.getCell(13);
                Cell answer6Cell = row.getCell(14);
                Cell fileQuestionCell = row.getCell(15);

                Cell correctAnswersCell = row.getCell(16);
                Cell pointCell = row.getCell(17);


                String sectionName = (sectionNameCell != null) ? sectionNameCell.getStringCellValue() : null;
                String descriptionSection = (descriptionSectionCell != null) ? descriptionSectionCell.getStringCellValue() : null;
                String fileSection = (fileSectionCell != null) ? fileSectionCell.getStringCellValue() : null;
                String typeSection = (typeSectionCell!= null)? typeSectionCell.getStringCellValue() : null;
                String questionContent = questionContentCell.getStringCellValue();
                String questionType = questionTypeCell.getStringCellValue();
                String answer1 = (answer1Cell != null) ? answer1Cell.getStringCellValue() : null;
                String answer2 = (answer2Cell != null) ? answer2Cell.getStringCellValue() : null;
                String answer3 = (answer3Cell != null) ? answer3Cell.getStringCellValue() : null;
                String answer4 = (answer4Cell != null) ? answer4Cell.getStringCellValue() : null;

                String answer5 = (answer5Cell != null) ? answer5Cell.getStringCellValue() : null;
                String answer6 = (answer6Cell != null) ? answer6Cell.getStringCellValue() : null;
                String fileQuestion = (fileQuestionCell != null) ? fileQuestionCell.getStringCellValue() : null;
                Float point =(float) pointCell.getNumericCellValue();
                String correctAnswers = (correctAnswersCell != null) ? correctAnswersCell.getStringCellValue() : null;
//                if (!examName.equals(currentExamName)) {
//                    currentExamName = examName;
//                    exam.setName(currentExamName);
//                }
                if (StringUtils.hasText(sectionName) && !sectionName.equals(currentNameSection)) {
                    currentNameSection = sectionName;
                    log.info("Tên section khác la : {}", fileSection);
//                    Section section = new Section();
                    currentSection = Section.builder()
                            .title(sectionName)
                            .questions(new ArrayList<>())
                            .type(typeSection)
                            .file(fileSection)
                            .description(descriptionSection)
                            .exam(exam).build();
                    sectionRepository.save(currentSection);
                }
                List<Integer> correctAnswersList = new ArrayList<>();
                if (correctAnswers != null) {
                    String[] correctAnswersArray = correctAnswers.split(",");
                    for (String answer : correctAnswersArray) {
                        try {
                            int value = Integer.parseInt(answer.trim());
                            correctAnswersList.add(value);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    correctAnswersList = null;
                }


                Question question = Question.builder().content(questionContent)
                        .section(currentSection)
                        .questionType(questionType)
                        .point(point)
                        .description(fileQuestion)
                        .choiceCorrect(correctAnswersList).build();

                List<Answer> answers = new ArrayList<>();
                answers.add(new Answer(answer1, 0, question));
                answers.add(new Answer(answer2, 1, question));
                answers.add(new Answer(answer3, 2, question));
                answers.add(new Answer(answer4, 3, question));
                if (StringUtils.hasText(answer5)) {
                    answers.add(new Answer(answer5, 4, question));
                }
                if (StringUtils.hasText(answer6)) {
                    answers.add(new Answer(answer6, 5, question));
                }
                question.setListAnswer(answers);
                if (currentSection != null) {
                    questionRepository.save(question);
                    List<Question> questionList = currentSection.getQuestions();
                    questionList.add(question);
                    currentSection.setQuestions(questionList);
                }


//                log.info("Thành công {}", exam.getName());
            }


            file.close();
            workbook.close();
            return exam;

        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

}
