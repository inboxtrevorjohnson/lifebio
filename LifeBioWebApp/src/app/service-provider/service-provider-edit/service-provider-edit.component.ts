import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { ActivatedRoute, Router } from '@angular/router';
import { ServiceProviderService} from '../../shared/service-provider/service-provider.service';
import {ServiceProvider} from '../../shared/service-provider/ServiceProvider';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {BootstrapAlert, BootstrapAlertService} from 'ngx-bootstrap-alert';
import {AppConstants} from '../../shared/app-constants';
import {CustomErrorHandler} from '../../shared/custom-error-handler';
import {HttpErrorResponse} from '@angular/common/http';

  @Component({
    selector: 'app-service-provider-edit',
    templateUrl: './service-provider-edit.component.html',
    styleUrls: ['./service-provider-edit.component.css']
  })

  export class ServiceProviderEditComponent implements OnInit, OnDestroy {
    serviceProvider:  ServiceProvider;
    subscription: Subscription;
    title = 'service provider';
    serviceProviderForm = new FormGroup ({
      serviceProviderName: new FormControl('',
        [Validators.required,
        Validators.minLength(3)]),
      practiseNumber: new FormControl('',
        [Validators.required,
          Validators.minLength(3)])
    });
    addingNew: boolean;

    constructor(private customErrorHandler: CustomErrorHandler,
                private bootstrapAlertService: BootstrapAlertService,
                private route: ActivatedRoute,
                private router: Router,
                private serviceProviderService: ServiceProviderService) {
  }

  ngOnInit() {
    this.subscription = this.route.params.subscribe(params => {
      const oid = params['oid'];
      if (oid) {
        this.addingNew = false;
        this.serviceProviderService.findServiceProvider(oid).subscribe((serviceProvider: ServiceProvider) => {
          if (serviceProvider) {
            this.serviceProvider = serviceProvider;
            // Set control values
            this.title = 'Edit: ' + this.title + ' - ' + this.serviceProvider.serviceProviderName;
            this.serviceProviderForm.get('serviceProviderName').setValue(this.serviceProvider.serviceProviderName);
            this.serviceProviderForm.get('practiseNumber').setValue(this.serviceProvider.practiseNumber);
          } else {
            console.log(`Service Provider with oid '${oid}' not found, returning to list`);
            this.serviceProviderService.goToList(this.router);
          }
        });
      } else {
        this.addingNew = true;
        this.title = 'Add a new service provider';
      }
    });
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  get serviceProviderName() { return this.serviceProviderForm.get('serviceProviderName'); }

  get practiseNumber() { return this.serviceProviderForm.get('practiseNumber'); }

  saveServiceProvider() {
      if (this.addingNew) {
        this.addServiceProvider();
      } else {
        this.changeServiceProvider();
      }
  }

  addServiceProvider() {
    this.serviceProvider = new ServiceProvider();
    this.serviceProvider.oid = null;
    this.serviceProvider.serviceProviderName = this.serviceProviderForm.get('serviceProviderName').value;
    this.serviceProvider.practiseNumber = this.serviceProviderForm.get('practiseNumber').value;
    this.serviceProvider.lastModified = null;
    this.serviceProviderService.addServiceProvider(this.serviceProvider)
      .subscribe(() => this.serviceProviderService.goToList(this.router), (error: HttpErrorResponse)=> {
        console.log('error = ' + error.type);
        this.showErrorModal(error); } );
  }

  changeServiceProvider() {
    this.serviceProvider.serviceProviderName = this.serviceProviderForm.get('serviceProviderName').value;
    this.serviceProvider.practiseNumber = this.serviceProviderForm.get('practiseNumber').value;
    this.serviceProviderService.changeServiceProvider(this.serviceProvider)
      .subscribe(() => this.serviceProviderService.goToList(this.router), error => this.showErrorModal(error));
  }

  private showErrorModal(error: HttpErrorResponse) {
    const alert = new BootstrapAlert(this.customErrorHandler.showError(error), 'alert-danger');
    alert.timeoutInMiliSeconds = AppConstants.ERROR_ALERT_TIMEOUT;
    this.bootstrapAlertService.alert(alert);
  }
}
