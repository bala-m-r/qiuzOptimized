import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { Question } from '../../models/model';
import { QuestionService } from '../../service/question.service';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { ButtonModule } from 'primeng/button';
import { FormArray, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { CheckboxModule } from 'primeng/checkbox';
import { RadioButtonModule } from 'primeng/radiobutton';
import { ResultsService } from '../../service/results.service';

@Component({
  selector: 'app-question',
  standalone: true,
  imports: [HttpClientModule, CommonModule, FormsModule, RadioButtonModule, ButtonModule, ReactiveFormsModule, InputTextModule, CheckboxModule],
  templateUrl: './question.component.html',
  styleUrl: './question.component.css',
  providers: [QuestionService, HttpClient, ResultsService]
})
export class QuestionComponent implements OnChanges, OnInit {


  @Input() isTest: boolean = false;
  @Input() rollno!: number;
  @Input() subjectcodeForQuestions!: string;

  @Output() isSubjectListForStudent = new EventEmitter<boolean>();
  
  questions: Question[] = [];
  selectedAnswers: any[] = [];
  questionForm!: FormGroup;
  isAdd = false;
  isEdit = false;
  @Input() isQuestionList:boolean = true;
  questionid!: number;
  quizSubmitted: boolean = false;
  score: number = 0;

  constructor(private questionService: QuestionService, private fb: FormBuilder, private resultsService: ResultsService) {
  }

  ngOnInit(){
    this.isQuestionList = true;
  }

  // ngOnChanges(changes: SimpleChanges): void {
  //   if (changes['subjectIdForQuestion'] && changes['subjectIdForQuestion'].currentValue) {
  //     console.log('onchanges', this.subjectIdForQuestion);
  //     this.getQuestions(this.subjectIdForQuestion);
  //   }
  // }

  // ngOnChanges(changes: SimpleChanges): void {
  //   if(this.subjectcodeForQuestions){
  //     console.log('onchanges',this.subjectcodeForQuestions);
  //     this.getQuestions(this.subjectcodeForQuestions);
  //   }
  // }
  ngOnChanges(changes: SimpleChanges): void {
    if (changes['subjectcodeForQuestions'] && changes['subjectcodeForQuestions'].currentValue) {
      this.getQuestions(this.subjectcodeForQuestions);
    }
  }

  getQuestions(subjectcode: string) {
    this.questionService.getQuestions(subjectcode).subscribe(
      (data) => {
        this.questions = data;
      });
  }

  deleteQuestion(id: number) {
    this.questionService.deleteQuestion(id).subscribe(() => {
      this.getQuestions(this.subjectcodeForQuestions);
    });
  }
  initializeForm() {
    this.questionForm = this.fb.group({
      question: ['', Validators.required],
      options: this.fb.array([this.createOption()])
    });
  }

  get optionControls() {
    return this.questionForm.get('options') as FormArray;
  }

  createOption(option?: { answer: string, isright: boolean }): FormGroup {
    return this.fb.group({
      answer: [option?.answer || '', Validators.required],  // Set the value or an empty string
      isright: [option?.isright || false]  // Set the value or default to false
    });
  }

  setOptions(options: { answer: string, isright: boolean }[]): void {
    // Explicitly define this as a FormArray containing FormGroup elements
    const optionsArray = this.fb.array(
      options.map(option => this.createOption(option))  // Map options to FormGroup
    );
  
    this.questionForm.setControl('options', optionsArray);  // Set the FormArray in the form
  }

  addOption() {
    this.optionControls.push(this.createOption());
  }

  removeOption(index: number) {
    this.optionControls.removeAt(index);
  }

  submitForm() {
    if (this.questionForm.valid) {
      const questionData = this.questionForm.value;
      if (this.isAdd) {
        this.addQuestion(questionData);
      } else {
        this.editQuestion(questionData);
      }
    }
  }

  get options(): FormArray {
    return this.questionForm.get('options') as FormArray;
  }

  editExistingQuestion(question: Question) {

    this.questionid = question.id;

    this.questionForm = this.fb.group({
      question: [question.question, Validators.required],
      options: this.fb.array([])  // Initialize as empty FormArray
    });

    this.setOptions(question.options);

    this.isEdit = true;
    this.isQuestionList = false;
    this.isAdd = false;
    this.isTest = false;

  }

  editQuestion(question: Question) {
    const questionText = question.question;
    const options = question.options.map((opt: any) => opt.answer);
    const isrights = question.options.map((opt: any) => opt.isright);

   

    this.questionService.editQuestion(this.questionid, questionText, this.subjectcodeForQuestions, options, isrights)
      .subscribe({
        next: (response) => {
          console.log('Question edited successfully:', response);
          this.questionForm.reset();
        },
        error: (err) => {
          console.error('Error editing question:', err);
        }
      });
  }
 
  addNewQuestion() {
    this.isAdd = true;
    this.isQuestionList = false;
    this.isEdit = false;
    this.isTest = false;
    this.initializeForm();

  }
  addQuestion(question: Question) {
    const questionText = question.question;
    const options = question.options.map((opt: any) => opt.answer);
    const isrights = question.options.map((opt: any) => opt.isright);

    this.questionService.addQuestion(questionText, this.subjectcodeForQuestions, options, isrights)
      .subscribe({
        next: (response) => {
          console.log('Question added successfully');
          this.questionForm.reset();
        },
        error: (err) => {
          console.error('Error adding question:', err);
        }
      });
  }
  toQuestions() {
    this.isAdd = false;
    this.isEdit = false;
    this.isTest = false;
    this.isQuestionList = true;
    this.getQuestions(this.subjectcodeForQuestions);

  }

  submitQuiz(): void {
    this.quizSubmitted = true;
    this.score = this.calculateScore();

    console.log(this.score, this.rollno, this.subjectcodeForQuestions, this.questions.length );

    // Call the service to save the result
    this.resultsService.addResult(this.rollno, this.subjectcodeForQuestions, this.score, this.questions.length)
      .subscribe({
        next: (response) => {
          console.log('Result saved successfully', response);
        },
        error: (err) => {
          console.error('Error saving result:', err);
        }
      });
  }

  calculateScore(): number {
    let correctAnswers = 0;
    this.questions.forEach((question, index) => {
      const selectedOption = this.selectedAnswers[index];
      if (selectedOption && selectedOption.isright) {
        correctAnswers++;
      }
    });
    return correctAnswers;
  }

  backToSubject(){
    this.isQuestionList = false;
    this.isSubjectListForStudent.emit(true);    
  }
}
