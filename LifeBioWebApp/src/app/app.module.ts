import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { ServiceProviderService } from './shared/service-provider/service-provider.service';
import { HttpClientModule } from '@angular/common/http';

import { FormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';

import { AppComponent } from './app.component';
import { ServiceProviderListComponent } from './service-provider-list/service-provider-list.component';

import { MatButtonModule, MatCardModule, MatInputModule, MatListModule, MatToolbarModule } from '@angular/material';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ServiceProviderEditComponent } from './service-provider-edit/service-provider-edit.component';

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
    BrowserModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatCardModule,
    MatInputModule,
    MatListModule,
    MatToolbarModule,
    FormsModule,
    RouterModule.forRoot(appRoutes)
  ],
  providers: [ServiceProviderService],
  bootstrap: [AppComponent]
})
export class AppModule { }
