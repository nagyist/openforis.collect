import { Routes, RouterModule }         from '@angular/router';

//Layouts
import { FullLayoutComponent }          from './layouts/full-layout.component';
import { SimpleLayoutComponent }        from './layouts/simple-layout.component';

//Pages
import { p404Component }                from './pages/404.component';
import { p500Component }                from './pages/500.component';
import { LoginComponent }               from './pages/login.component';
import { RegisterComponent }            from './pages/register.component';

const appRoutes: Routes = [
    {
        path: '',
        redirectTo: 'dashboard',
        pathMatch: 'full',
    },
    {
        path: '',
        component: FullLayoutComponent,
        data: {
            title: 'Home'
        },
        children: [
            {
                path: 'dashboard',
                loadChildren: 'app/dashboard/dashboard.module#DashboardModule'
            },
            {
                path: 'data-management',
                loadChildren: 'app/data-management/data-management.module#DataManagementModule'
            },
            {
                path: 'survey-designer',
                loadChildren: 'app/survey-designer/survey-designer.module#SurveyDesignerModule'
            },
//            {
//                path: 'components',
//                redirectTo: 'components/buttons',
//                pathMatch: 'full',
//            },
//            {
//                path: 'components',
//                data: {
//                    title: 'Components'
//                },
//                children: [
//                    {
//                        path: 'buttons',
//                        component: ButtonsComponent,
//                        data: {
//                            title: 'Buttons'
//                        }
//                    },
//                    {
//                        path: 'cards',
//                        component: CardsComponent,
//                        data: {
//                            title: 'Cards'
//                        }
//                    },
//                    {
//                        path: 'forms',
//                        component: FormsComponent,
//                        data: {
//                            title: 'Forms'
//                        }
//                    },
//                    {
//                        path: 'social-buttons',
//                        component: SocialButtonsComponent,
//                        data: {
//                            title: 'Social buttons'
//                        }
//                    },
//                    {
//                        path: 'switches',
//                        component: SwitchesComponent,
//                        data: {
//                            title: 'Switches'
//                        }
//                    },
//                    {
//                        path: 'tables',
//                        component: TablesComponent,
//                        data: {
//                            title: 'Tables'
//                        }
//                    }
//                ]
//            },
//            {
//                path: 'icons',
//                redirectTo: 'icons/font-awesome',
//                pathMatch: 'full',
//            },
//            {
//                path: 'icons',
//                data: {
//                    title: 'Icons'
//                },
//                children: [
//                    {
//                        path: 'font-awesome',
//                        component: FontAwesomeComponent,
//                        data: {
//                            title: 'Font Awesome'
//                        }
//                    },
//                    {
//                        path: 'simple-line-icons',
//                        component: SimpleLineIconsComponent,
//                        data: {
//                            title: 'Simple Line Icons'
//                        }
//                    }
//                ]
//            },
//            {
//                path: 'widgets',
//                component: WidgetsComponent,
//                data: {
//                    title: 'Widgets'
//                }
//            },
//            {
//                path: 'charts',
//                component: ChartsComponent,
//                data: {
//                    title: 'Charts'
//                }
//            }
        ]
    },
//    {
//        path: 'pages',
//        component: SimpleLayoutComponent,
//        data: {
//            title: 'Pages'
//        },
//        children: [
//            {
//                path: '404',
//                component: p404Component,
//                data: {
//                    title: 'Page 404'
//                }
//            },
//            {
//                path: '500',
//                component: p500Component,
//                data: {
//                    title: 'Page 500'
//                }
//            },
//            {
//                path: 'login',
//                component: LoginComponent,
//                data: {
//                    title: 'Login Page'
//                }
//            },
//            {
//                path: 'register',
//                component: RegisterComponent,
//                data: {
//                    title: 'Register Page'
//                }
//            }
//        ]
//    }
];

export const routing = RouterModule.forRoot(appRoutes);