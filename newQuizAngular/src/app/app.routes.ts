import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { SubjectlistComponent } from './components/subjectlist/subjectlist.component';
import { StudentlistComponent } from './components/studentlist/studentlist.component';


export const routes: Routes = [
  { path:'subjects', component: SubjectlistComponent},
  { path:'student', component: StudentlistComponent}
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
  })

  export class AppRoutingModule {}