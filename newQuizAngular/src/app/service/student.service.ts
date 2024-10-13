import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Student } from '../models/model';
import { Observable } from 'rxjs/internal/Observable';

@Injectable({
  providedIn: 'root'
})
export class StudentService {

  apiUrl = 'http://localhost:8080/student';
   

  constructor(private http : HttpClient) { }


  getStudents(): Observable<Student[]> {
    return this.http.get<Student[]>(`${this.apiUrl}/getAll`);
  }

  addSubject(student: Student): Observable<Student> {
    return this.http.post<Student>(`${this.apiUrl}/add`, student);
  }

  deleteStudent(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/delete/${id}`);
  }
}
