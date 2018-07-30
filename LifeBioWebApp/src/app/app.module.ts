import { BrowserModule } from '@angular/platform-browser';
import {ErrorHandler, NgModule} from '@angular/core';
import { ServiceProviderService } from './shared/service-provider/service-provider.service';
import { HttpClientModule } from '@angular/common/http';

import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';

import { AppComponent } from './app.component';
import { AppBootstrapModule } from './app-bootstrap/app-bootstrap.module';
import { BootstrapAlertModule } from 'ngx-bootstrap-alert';
import { ModalModule } from 'ngx-bootstrap/modal';
import { MatIconModule } from '@angular/material';

import { ServiceProviderListComponent } from './service-provider-list/service-provider-list.component';
import { ServiceProviderEditComponent } from './service-provider-edit/service-provider-edit.component';
import {CustomErrorHandler} from './shared/custom-error-handler';

const appRoutes: Routes = [
  { path: '', redirectTo: '/service-provider-list', pathMatch: 'full' },
  {
    path: 'service-provider-list',
    component: ServiceProviderListComponent
  },
  {
    path: 'service-provider-add',
    component: ServiceProviderEditComponent
  },
  {
    path: 'service-provider-edit/:oid',
    component: ServiceProviderEditComponent
  }
];

@NgModule({
  declarations: [
    AppComponent,
    ServiceProviderListComponent,
    ServiceProviderEditComponent
  ],
  imports: [
    AppBootstrapModule,
    BootstrapAlertModule,
    BrowserModule,
    HttpClientModule,
    FormsModule,
    MatIconModule,
    ModalModule.forRoot(),
    ReactiveFormsModule,
    RouterModule.forRoot(appRoutes)
  ],
  providers: [
    CustomErrorHandler,
    ServiceProviderService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
