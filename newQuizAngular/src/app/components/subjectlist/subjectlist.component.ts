import { Component, EventEmitter, Input, OnChanges, output, Output, SimpleChanges } from '@angular/core';
import { Subject } from '../../models/model';
import { SubjectlistService } from '../../service/subjectlist.service';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { InputTextModule } from 'primeng/inputtext';
import { TooltipModule } from 'primeng/tooltip';
import { ButtonModule } from 'primeng/button';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { QuestionComponent } from '../question/question.component';
import { MessageService } from 'primeng/api';
import { MessagesModule } from 'primeng/messages';
import { TableModule } from 'primeng/table';
import { ResultsComponent } from '../results/results.component';

@Component({
  selector: 'app-subjectlist',
  standalone: true,
  imports: [HttpClientModule, ResultsComponent, CommonModule, TableModule,  MessagesModule, ReactiveFormsModule, TooltipModule, ButtonModule, InputTextModule, FormsModule, QuestionComponent],
  templateUrl: './subjectlist.component.html',
  styleUrl: './subjectlist.component.css',
  providers: [SubjectlistService, HttpClient, MessageService]
})
export class SubjectlistComponent implements OnChanges{


  
  isQuestionList: boolean = false;
  isTest: boolean = false;
  isResult: boolean = false;
  subjectcode: string = '';

  subjects: Subject[] = [];

  errorMessage: string | null = null;

  @Input() isSubjectList: boolean = true;
  @Input() isSubjectListForStudent: boolean = false;
  @Input() rollno!: number;

  
  @Output() isStudent = new EventEmitter<boolean>();
  @Output() isStudentList = new EventEmitter<boolean>();

  addSubjectForm!: FormGroup;
  // newSubject: Subject = { id: 0, subjectcode :'', subject: ''};
  subjectcodeForQuestions: string = '';
  // subjectIdForQuestion: number = 0;

  constructor(private subjectlistService: SubjectlistService, private fb: FormBuilder){
    this.getSubjects();
    
  }
  ngOnChanges(): void {
    console.log(this.isSubjectList);
    console.log(this.isSubjectListForStudent);
   
  }
  

  ngOnInit() {
    this.isSubjectList = true;
    // Initialize the form with form controls
    this.addSubjectForm = this.fb.group({
      subjectcode: ['', [Validators.required]],
      subject: ['', [Validators.required]],
    });
  }

  getSubjects(): void {
    this.subjectlistService.getSubjects().subscribe((data) => {
      this.subjects = data;
    });
  }

  deleteSubject(index: number): void {
    this.subjectlistService.deleteSubject(this.subjects[index].id).subscribe(() => {
      this.getSubjects();  // Refresh the list after deletion
    });
  }
  
    
  selectSubject(index: number) {
    this.subjectcodeForQuestions = this.subjects[index].subjectcode;
    this.isSubjectList = false;
    this.isQuestionList = true;
    this.isResult = false;
    this.isSubjectListForStudent = false;
    this.isTest = false;
    
  }

  addSubject(): void {
    if (this.addSubjectForm.valid) {
      const newSubject = this.addSubjectForm.value;
      this.subjectlistService.addSubject(newSubject).subscribe(
        () => {
          this.errorMessage = null;
          this.getSubjects();
          this.addSubjectForm.reset();
        },
        (err) => {
          this.errorMessage = 'Subject Code Already Exists';
          this.addSubjectForm.reset();
          
        }
      );
    }
  }

  viewProgress(subjectcode: string) {
    this.isResult = true;
    this.isSubjectList = false;

    this.subjectcode = subjectcode;
  }

  taketest(subjectcode: string) {
    this.isQuestionList = true;
    this.isSubjectList = false;

    this.isResult = false;
    this.isSubjectListForStudent = false;
    
    this.subjectcodeForQuestions = subjectcode;
    this.isTest = true;
  }

  backToSubjectForStudent(){
    this.isQuestionList = false;
    this.isSubjectList = true;
    this.isSubjectListForStudent = true;
    this.isTest = true;
  }

  subjectListForStudent(data: boolean){
    this.isSubjectListForStudent = data;
    this.isSubjectList = data;
  }

  backToSubject(){
    this.isSubjectList = true;
    this.isQuestionList = false;

    this.isResult = false;
    this.isSubjectListForStudent = false;
    this.isTest = false;
  }

  backToStudent(){
    this.isSubjectListForStudent = false;
    this.isSubjectList = false;

    this.isQuestionList = false;
    this.isResult = false;
 
    this.isTest = false;
    this.isStudent.emit(true);
    // this.isStudentList.emit(true);
    
  }

  result(data : boolean){
    this.isResult = data
  }

  subjectList(data: boolean){
    this.isSubjectList = true;
  }

  

}
