import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Subject } from '../models/model';

@Injectable({
  providedIn: 'root'
})
export class SubjectlistService {

  apiUrl = 'http://localhost:8080/subject';
   

  constructor(private http : HttpClient) { }


  getSubjects(): Observable<Subject[]> {
    return this.http.get<Subject[]>(`${this.apiUrl}/getAll`);
  }

  addSubject(subject: Subject): Observable<Subject> {
    return this.http.post<Subject>(`${this.apiUrl}/add`, subject);
  }

  deleteSubject(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/delete/${id}`);
  }
}
