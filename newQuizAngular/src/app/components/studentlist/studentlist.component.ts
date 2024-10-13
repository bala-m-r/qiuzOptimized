import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { StudentService } from '../../service/student.service';
import { Student } from '../../models/model';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { TooltipModule } from 'primeng/tooltip';
import { MessagesModule } from 'primeng/messages';
import { TableModule } from 'primeng/table';
import { SubjectlistComponent } from '../subjectlist/subjectlist.component';
import { ResultsService } from '../../service/results.service';
import { ResultsComponent } from '../results/results.component';

@Component({
  selector: 'app-studentlist',
  standalone: true,
  imports: [SubjectlistComponent, ResultsComponent, CommonModule, ButtonModule, TableModule, ReactiveFormsModule, HttpClientModule, TooltipModule, MessagesModule],
  templateUrl: './studentlist.component.html',
  styleUrl: './studentlist.component.css',
  providers: [StudentService, HttpClient, FormBuilder, ResultsService]
})
export class StudentlistComponent {


  addStudentForm!: FormGroup;
  errorMessage: string | null = null;
  students: Student[] = [];
  rollno!: number;

  isResult: boolean = false;
  isStudentList: boolean = true;
  isSubjectListForStudent: boolean = false;
  isSubjectList: boolean = true;

  constructor(private studentService: StudentService, private formBuilder: FormBuilder) {
    this.getStudentList();
  }
  fromSubject(data: boolean) {
    this.isStudentList = data;
  }

  ngOnInit() {

    this.isStudentList = true;

    this.addStudentForm = this.formBuilder.group({
      rollno: [, [Validators.required]],
      name: ['', [Validators.required]]
    });

  }

  getStudentList(): void {
    this.studentService.getStudents().subscribe((data) => {
      this.students = data;
    });
  }

  addStudent() {
    if (this.addStudentForm.valid) {
      const newStudent = this.addStudentForm.value;
      if (typeof newStudent.rollno != 'number') {
        this.errorMessage = 'Roll Number should be a Number'
      }
      this.studentService.addSubject(newStudent).subscribe(
        () => {
          this.errorMessage = null;
          this.getStudentList();
          this.addStudentForm.reset();
        },
        (err) => {
          if (this.errorMessage == null) {
            this.errorMessage = 'Roll Number Already Exists';
          }

          this.addStudentForm.reset();
        }
      );
    }
  }
  deleteStudent(id: number): void {
    this.studentService.deleteStudent(id).subscribe(() => {
      this.getStudentList();  // Refresh the list after deletion
    });
  }
  viewProgress(rollno: number) {
    this.rollno = rollno;
    this.isStudentList = false;
    this.isResult = true;
  }
  selectStudent(rollno: number) {
    console.log('select student', rollno, this.isStudentList, this.isSubjectListForStudent, this.isSubjectList);
    this.isStudentList = false;
    this.isSubjectListForStudent = true;
    this.isSubjectList = true;
    console.log('select student', rollno, this.isStudentList, this.isSubjectListForStudent, this.isSubjectList);
    this.rollno = rollno;
  }

  backToStudent(){
    this.isStudentList = true;
    this.isSubjectListForStudent = false;
    this.isSubjectList = false;
  }

  studentList(data: boolean) {
    this.isStudentList = data;
  }
  result(data: boolean) {
    this.isResult = data;
  }
  backStudentList(data : boolean){
    this.isStudentList = data;
  }

}
