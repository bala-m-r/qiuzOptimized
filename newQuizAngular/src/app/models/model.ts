export interface Question {
  id : number;
  question : string;
  subjectcode : string;  
  options: Answer[];  
}

export interface Answer {
  id: number;
  answer : string;
  isright: boolean;
}
export interface Student{
  id: number,
  rollno: number;
  name: string,
}
  
export interface Subject {
  id : number;
  subjectcode: string;
  subject: string; 
}
  
export interface Results {
  id: number;
  mark: number;
  rollno: number;
  subjectcode: string;
}