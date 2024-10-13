import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';

@Injectable({
  providedIn: 'root'
})
export class ResultsService {

  private apiUrl = 'http://localhost:8080/results';

  constructor(private http: HttpClient) { }

  getResults(page: number, size: number | undefined, sortField: string | string[], sortOrder: string): Observable<any> {
    
    return this.http.get(`${this.apiUrl}/getPage?page=${page}&size=${size}&sort=${sortField},${sortOrder}`);
  }

  addResult(rollno: number, subjectcode: string, mark: number, fullmark: number): Observable<any> {

    const formData = new FormData();
    formData.append('mark', mark.toString());
    formData.append('fullmark', fullmark.toString());
    formData.append('rollno', rollno.toString());
    formData.append('subjectcode', subjectcode);
    

    return this.http.post(`${this.apiUrl}/add`, formData);
  }

  searchResultsForStudent(page: number, size: number, searchQuery: string, sortField: string, sortOrder: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/search/student`, {
      params: {
        page: page.toString(),
        size: size.toString(),
        search: searchQuery,
        sortField: sortField,
        sortOrder: sortOrder
      }
    });
  }

  // Search results by subject criteria
  searchResultsForSubject(page: number, size: number, searchQuery: string, sortField: string, sortOrder: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/search/subject`, {
      params: {
        page: page.toString(),
        size: size.toString(),
        search: searchQuery,
        sortField: sortField,
        sortOrder: sortOrder
      }
    });
  }
}
