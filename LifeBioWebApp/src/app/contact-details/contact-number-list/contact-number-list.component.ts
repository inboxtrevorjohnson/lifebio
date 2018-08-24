import {Component, Input, OnChanges, OnDestroy, OnInit, TemplateRef} from '@angular/core';
import {BootstrapAlertService, BootstrapAlert } from 'ngx-bootstrap-alert';
import {BsModalRef, BsModalService} from 'ngx-bootstrap';
import {AppConstants} from '../../shared/app-constants';
import {CustomErrorHandler} from '../../shared/custom-error-handler';
import {HttpErrorResponse} from '@angular/common/http';
import {Subscription} from 'rxjs/Subscription';
import {ContactDetails, ContactNumber} from '../../shared/contact-details/ContactDetails';
import {ContactDetailsService} from '../../shared/contact-details/contact-details.service';

@Component({
  selector: 'app-contact-number-list',
  templateUrl: './contact-number-list.component.html',
  styleUrls: ['./contact-number-list.component.css']
})
export class ContactNumberListComponent implements OnInit, OnDestroy {

  subscription: Subscription;
  public deleteModalRef: BsModalRef;
  contactNumbers: Array<ContactNumber>;
  private _contactNumber: ContactNumber;

  constructor(private customErrorHandler: CustomErrorHandler,
              private bootstrapAlertService: BootstrapAlertService,
              private modalService: BsModalService,
              private contactDetailsService: ContactDetailsService) {
  }

  ngOnInit() {
    if(!this.contactDetailsService.contactDetailsOID) {
      console.log('Details Null');
      return;
    }

    this.subscription = this.contactDetailsService.findAllContactNumbers(this.contactDetailsService.contactDetailsOID)
      .subscribe(data => {
        this.contactNumbers = data;
      }, error => this.showErrorModal(error));
  }

  ngOnDestroy() {
     this.subscription.unsubscribe();
  }

  remove(contactNumber: ContactNumber) {
    this.contactDetailsService.removeContactNumber(contactNumber).subscribe(result => {
      this.contactDetailsService.findAllContactNumbers(this.contactDetailsService.contactDetailsOID).subscribe(data => {
        this.contactNumbers = data;
      });
    }, error => this.showErrorModal(error));
    this.deleteModalRef.hide();
  }

  setContactNumber(contactNumber: ContactNumber) {
    this._contactNumber = contactNumber;
  }

  get contactNumber() {
    return this._contactNumber;
  }

  public openDeleteModal(contactNumber: ContactNumber, template: TemplateRef<any>) {
    this._contactNumber = contactNumber;
    this.deleteModalRef = this.modalService.show(template);
  }

  private showErrorModal(error: HttpErrorResponse) {
    const alert = new BootstrapAlert(this.customErrorHandler.showError(error), 'alert-danger');
    alert.timeoutInMiliSeconds = AppConstants.ERROR_ALERT_TIMEOUT;
    this.bootstrapAlertService.alert(alert);
  }
}
