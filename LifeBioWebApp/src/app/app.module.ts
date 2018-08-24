import { BrowserModule } from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';

import { AppBootstrapModule } from './app-bootstrap/app-bootstrap.module';
import { BootstrapAlertModule } from 'ngx-bootstrap-alert';
import { CollapseModule } from 'ngx-bootstrap/collapse';
import { ModalModule } from 'ngx-bootstrap/modal';
import { TabsModule } from 'ngx-bootstrap/tabs';

import { MatButtonModule, MatCardModule, MatDividerModule, MatIconModule, MatMenuModule,} from '@angular/material';

import { AppComponent } from './app.component';
import { CustomErrorHandler} from './shared/custom-error-handler';
import { ContactDetailsComponent } from './contact-details/contact-details.component';
import { NavigationMenuComponent } from './navigation-menu/navigation-menu.component';
import { ServiceProviderService } from './shared/service-provider/service-provider.service';
import { ServiceProviderListComponent } from './service-provider/service-provider-list/service-provider-list.component';
import { ServiceProviderEditComponent } from './service-provider/service-provider-edit/service-provider-edit.component';
import {ContactDetailsService} from './shared/contact-details/contact-details.service';
import { ContactNumberListComponent } from './contact-details/contact-number-list/contact-number-list.component';
import { ContactNumberEditComponent } from './contact-details/contact-number-edit/contact-number-edit.component';

const appRoutes: Routes = [
  {
    path: '',
    redirectTo: '/service-provider-list',
    pathMatch: 'full'
  },
  {
    path: 'contact-details/:parentObjectType/:parentOID/:contactDetailsOID',
    component: ContactDetailsComponent
  },
  {
    path: 'contact-numbers-list',
    component: ContactNumberListComponent
  },
  {
    path: 'contact-number-add',
    component: ContactNumberEditComponent
  },
  {
    path: 'contact-number-edit/:oid',
    component: ContactNumberEditComponent
  },
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
    ServiceProviderEditComponent,
    ContactDetailsComponent,
    NavigationMenuComponent,
    ContactNumberListComponent,
    ContactNumberEditComponent
  ],
  imports: [
    AppBootstrapModule,
    BootstrapAlertModule,
    BrowserModule,
    CollapseModule.forRoot(),
    HttpClientModule,
    FormsModule,
    MatButtonModule,
    MatCardModule,
    MatDividerModule,
    MatIconModule,
    MatMenuModule,
    ModalModule.forRoot(),
    ReactiveFormsModule,
    RouterModule.forRoot(appRoutes),
    TabsModule.forRoot()
  ],
  providers: [
    ContactDetailsService,
    CustomErrorHandler,
    ServiceProviderService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
