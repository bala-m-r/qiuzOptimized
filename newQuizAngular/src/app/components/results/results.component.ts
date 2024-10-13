import { ChangeDetectorRef, Component, EventEmitter, Input, Output } from '@angular/core';
import { ResultsService } from '../../service/results.service';
import { LazyLoadEvent } from 'primeng/api';
import { ButtonModule } from 'primeng/button';
import { PaginatorModule } from 'primeng/paginator';
import { TableLazyLoadEvent, TableModule } from 'primeng/table';
import { CommonModule } from '@angular/common';
import { SubjectlistComponent } from '../subjectlist/subjectlist.component';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { FloatLabelModule } from 'primeng/floatlabel';

@Component({
  selector: 'app-results',
  standalone: true,
  imports: [ButtonModule, PaginatorModule, FloatLabelModule, TableModule, CommonModule, SubjectlistComponent, HttpClientModule],
  templateUrl: './results.component.html',
  styleUrl: './results.component.css',
  providers: [ResultsService, HttpClient]
})
export class ResultsComponent {

  @Input() subjectcode!: string;
  @Input() rollno!: number;
  @Input() isStudent: boolean = false;

  // this.isResult = true;
  //   this.isSubjectList = false;

  @Output() isResult = new EventEmitter<boolean>();
  @Output() isSubjectList = new EventEmitter<boolean>();
  @Output() isStudentList = new EventEmitter<boolean>();


  results: any[] = [];
  totalRecords: number = 0;
  loading: boolean = false;
  serachValue!: string;

  constructor(private resultsService: ResultsService, private cdr: ChangeDetectorRef){}

  ngOnInit() {
    this.loadResults({ first: 0, rows: 5 } as TableLazyLoadEvent);
  }

  onSearch() {
    this.loading = true;
  
    // If search value is empty, just reload all results without search filters
    if (!this.serachValue) {
      this.loadResults({ first: 0, rows: 5 } as TableLazyLoadEvent);
      return;
    }
  
    const page = 0;
    const size = 5;
    const sortField = 'mark'; // Default sort field
    const sortOrder = 'asc'; // Default sort order
  
    // Call search based on whether it's a student or subject view
    if (!this.isStudent) {
      this.resultsService.searchResultsForStudent(page, size, this.serachValue, sortField, sortOrder).subscribe(
        (response: any) => {
          this.results = response.content;
          this.totalRecords = response.totalElements;
          this.loading = false;
          this.cdr.detectChanges();
        },
        (error) => {
          console.error(error);
          this.loading = false;
        }
      );
    } else {
      this.resultsService.searchResultsForSubject(page, size, this.serachValue, sortField, sortOrder).subscribe(
        (response: any) => {
          this.results = response.content;
          this.totalRecords = response.totalElements;
          this.loading = false;
          this.cdr.detectChanges();
        },
        (error) => {
          console.error(error);
          this.loading = false;
        }
      );
    }
  }
  


  loadResults(event: TableLazyLoadEvent) {
    this.loading = true;

    const page = (event.first ?? 0) / (event.rows ?? 5);
    const size = event.rows ?? 5;
    const sortField = event.sortField ? event.sortField : 'mark';
    const sortOrder = event.sortOrder === 1 ? 'asc' : 'desc';

    this.resultsService.getResults(page, size, sortField, sortOrder).subscribe(
      (response: any) => {
        this.results = response.content;
        this.totalRecords = response.totalElements;
        this.loading = false;
        this.cdr.detectChanges();
      },
      (error) => {
        console.error(error);
        this.loading = false;
      }
    );
  }

  backTostudentList() {
    this.isResult.emit(false);
    this.isStudentList.emit(true);
    this.isStudent = false;
  }
  backToSubjectList() {
    this.isResult.emit(false);
    this.isSubjectList.emit(true);
  }

}
