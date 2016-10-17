import { NgModule }                     from '@angular/core';
import { BrowserModule }                from '@angular/platform-browser';
import { HttpModule }                   from '@angular/http';

import { AppComponent }                 from './app.component';
import { Ng2BootstrapModule }           from 'ng2-bootstrap/ng2-bootstrap';
import { NAV_DROPDOWN_DIRECTIVES }      from './shared/nav-dropdown.directive';
//import { NgaModule }                    from './theme/nga.module';

import { DataTableModule, SharedModule } from 'primeng/primeng';

//import { ChartsModule }                 from 'ng2-charts/ng2-charts';
import { SIDEBAR_TOGGLE_DIRECTIVES }    from './shared/sidebar.directive';
import { AsideToggleDirective }         from './shared/aside.directive';
import { BreadcrumbsComponent }         from './shared/breadcrumb.component';
import { routing }                      from './app.routing';

//Layouts
import { FullLayoutComponent }          from './layouts/full-layout.component';
import { SimpleLayoutComponent }        from './layouts/simple-layout.component';

//Main view
import { DashboardComponent }           from './dashboard/dashboard.component';

//Data Management
import { DataManagementComponent }      from './data-management/data-management.component';
import { RecordTableComponent }         from './data-management/record-table.component';
import { DataEntryComponent }           from './data-management/data-entry/data-entry.component';

//Survey Designer
import { SurveyDesignerHomeComponent }  from './survey-designer/survey-designer-home.component';
import { SurveyTableComponent }         from './survey-designer/survey-table.component';

//Pages
import { p404Component }                from './pages/404.component';
import { p500Component }                from './pages/500.component';
import { LoginComponent }               from './pages/login.component';
import { RegisterComponent }            from './pages/register.component';

@NgModule({
  imports: [
    BrowserModule,
    routing,
    Ng2BootstrapModule,
    HttpModule,
//    NgaModule.forRoot(),
    
    DataTableModule,
    SharedModule
  ],
  declarations: [
    AppComponent,
    FullLayoutComponent,
    SimpleLayoutComponent,
    
    NAV_DROPDOWN_DIRECTIVES,
    BreadcrumbsComponent,
    SIDEBAR_TOGGLE_DIRECTIVES,
    AsideToggleDirective,
    
    p404Component,
    p500Component,
    LoginComponent,
    RegisterComponent,
    
    DashboardComponent,
    
    SurveyDesignerHomeComponent,
    SurveyTableComponent,
    
    DataManagementComponent,
    DataEntryComponent,
    RecordTableComponent
  ],
  bootstrap: [ AppComponent ]
})
export class AppModule { }