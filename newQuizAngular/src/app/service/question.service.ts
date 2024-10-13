import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Question } from '../models/model';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class QuestionService {

  apiUrl = 'http://localhost:8080/question';

  constructor(private http: HttpClient) { }

  getQuestions(subjectcode : string): Observable<Question[]>{
    
    return this.http.get<Question[]>(`${this.apiUrl}/getBySubjectcode/${subjectcode}`);
  }

  // getQuestions(subjectid : number): Observable<Question[]>{
  //   console.log(subjectid);
  //   return this.http.get<Question[]>(`${this.apiUrl}/getBySubjectId/${subjectid}`);
  // }
  deleteQuestion(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/delete/${id}`);
  }

  addQuestion(questionText: string, subjectcode: string, options: string[], isright: boolean[]): Observable<Question> {
    const url = `${this.apiUrl}/add`;

    const question = {
        question: questionText,
        options: options.map((option, index) => ({
            answer: option,
            isright: isright[index]
        }))
    };
    return this.http.post<Question>(url, question, {
        params: new HttpParams().set('subjectcode', subjectcode),
        headers: new HttpHeaders({
          'Content-Type': 'application/json' 
        })
    });
  }
  editQuestion(id :number, questionText: string, subjectcode: string, options: string[], isright: boolean[]): Observable<Question> {
    const url = `${this.apiUrl}/edit/${id}`;

    const question = {
        id: id,
        question: questionText,
        options: options.map((option, index) => ({
            answer: option,
            isright: isright[index]
        }))
    };
    return this.http.post<Question>(url, question, {
        params: new HttpParams().set('subjectcode', subjectcode),
        headers: new HttpHeaders({
          'Content-Type': 'application/json' 
        })
    });
  }
}
