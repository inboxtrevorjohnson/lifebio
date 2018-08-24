import {Component, OnDestroy, OnInit, TemplateRef} from '@angular/core';
import {ServiceProviderService } from '../../shared/service-provider/service-provider.service';
import {ServiceProvider} from '../../shared/service-provider/ServiceProvider';
import {BootstrapAlertService, BootstrapAlert } from 'ngx-bootstrap-alert';
import {BsModalRef, BsModalService} from 'ngx-bootstrap';
import {AppConstants} from '../../shared/app-constants';
import {CustomErrorHandler} from '../../shared/custom-error-handler';
import {HttpErrorResponse} from '@angular/common/http';
import {Subscription} from 'rxjs/Subscription';

@Component({
  selector: 'app-service-provider-list',
  templateUrl: './service-provider-list.component.html',
  styleUrls: ['./service-provider-list.component.css']
})
export class ServiceProviderListComponent implements OnInit, OnDestroy {

  subscription: Subscription;
  public deleteModalRef: BsModalRef;
  serviceProviders: Array<ServiceProvider>;
  private _serviceProvider: ServiceProvider;
  parentObject = AppConstants.SERVICE_PROVIDER;


  constructor(private customErrorHandler: CustomErrorHandler,
              private bootstrapAlertService: BootstrapAlertService,
              private modalService: BsModalService,
              private serviceProviderService: ServiceProviderService) {
  }

  ngOnInit() {
      this.subscription = this.serviceProviderService.findAllServiceProviders()
        .subscribe(data => {
        this.serviceProviders = data;
      }, error => this.showErrorModal(error));
   }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  remove(serviceProvider: ServiceProvider) {
    this.serviceProviderService.removeServiceProvider(serviceProvider).subscribe(result => {
      this.serviceProviderService.findAllServiceProviders().subscribe(data => {
        this.serviceProviders = data;
      });
    }, error => this.showErrorModal(error));
    this.deleteModalRef.hide();
  }

  setServiceProvider(serviceProvider: ServiceProvider) {
    this._serviceProvider = serviceProvider;
  }

  get serviceProvider() {
    return this._serviceProvider;
  }

  public openDeleteModal(serviceProvider: ServiceProvider, template: TemplateRef<any>) {
    this._serviceProvider = serviceProvider;
    this.deleteModalRef = this.modalService.show(template);
  }

  private showErrorModal(error: HttpErrorResponse) {
    const alert = new BootstrapAlert(this.customErrorHandler.showError(error), 'alert-danger');
    alert.timeoutInMiliSeconds = AppConstants.ERROR_ALERT_TIMEOUT;
    this.bootstrapAlertService.alert(alert);
  }
}
