import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { ActivatedRoute, Router } from '@angular/router';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {BootstrapAlert, BootstrapAlertService} from 'ngx-bootstrap-alert';
import {AppConstants} from '../../shared/app-constants';
import {CustomErrorHandler} from '../../shared/custom-error-handler';
import {HttpErrorResponse} from '@angular/common/http';
import {ContactNumber, ContactType} from '../../shared/contact-details/ContactDetails';
import {ContactDetailsService} from '../../shared/contact-details/contact-details.service';

@Component({
  selector: 'app-contact-number-edit',
  templateUrl: './contact-number-edit.component.html',
  styleUrls: ['./contact-number-edit.component.css']
})

export class ContactNumberEditComponent implements OnInit, OnDestroy {
  contactTypes = [ContactType.EMPTY, ContactType.BUSINESS, ContactType.PERSONAL, ContactType.RESIDENTIAL];
  contactNumber:  ContactNumber;
  subscription: Subscription;
  title = 'Contact Number';
  contactNumberForm = new FormGroup ({
    number: new FormControl('',
      [Validators.required,
          Validators.pattern('[0-9]{10}')]),
    contactType: new FormControl('', Validators.required)
  });
  addingNew: boolean;
  parentURL: string;

  constructor(private customErrorHandler: CustomErrorHandler,
              private bootstrapAlertService: BootstrapAlertService,
              private route: ActivatedRoute,
              private router: Router,
              private contactDetailsService: ContactDetailsService) {
  }

  ngOnInit() {
    this.subscription = this.route.params.subscribe(params => {
      const oid = params['oid'];
      this.parentURL = '/contact-details/' + this.contactDetailsService.parentObjectType + '/' + this.contactDetailsService.parentOID +
        '/' + this.contactDetailsService.contactDetailsOID;
      if (oid) {
        this.addingNew = false;
        this.contactDetailsService.findContactNumber(oid).subscribe((contactNumber: ContactNumber) => {
          if (contactNumber) {
            this.contactNumber = contactNumber;
            // Set control values
            this.title = 'Edit: ' + this.title;
            this.contactNumberForm.get('number').setValue(this.contactNumber.number);
            this.contactNumberForm.get('contactType').setValue(this.contactNumber.contactType);
          } else {
            console.log(`Contact number with oid '${oid}' not found, returning to list`);
            this.contactDetailsService.goToList(this.router);
          }
        });
      } else if (this.contactDetailsService.contactDetailsOID) {
        this.addingNew = true;
        this.title = 'Add a new contact number';
      }
    });
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  get number() { return this.contactNumberForm.get('number'); }

  get contactType() { return this.contactNumberForm.get('contactType'); }

  saveContactNumber() {
    if (this.addingNew) {
      this.addContactNumber();
    } else {
      this.changeContactNumber();
    }
  }

  addContactNumber() {
    this.contactNumber = new ContactNumber();
    this.contactNumber.oid = null;
    this.contactNumber.contactType = this.contactNumberForm.get('contactType').value;
    this.contactNumber.number = this.contactNumberForm.get('number').value;
    this.contactNumber.lastModified = null;
    this.contactDetailsService.addContactNumber(this.contactDetailsService.contactDetailsOID, this.contactNumber)
      .subscribe(() => this.contactDetailsService.goToList(this.router), (error: HttpErrorResponse)=> {
        console.log('error = ' + error.message);
        this.showErrorModal(error); } );
  }

  changeContactNumber() {
    this.contactNumber.contactType = this.contactNumberForm.get('contactType').value;
    this.contactNumber.number = this.contactNumberForm.get('number').value;
    this.contactDetailsService.changeContactNumber(this.contactNumber)
      .subscribe(() => this.contactDetailsService.goToList(this.router), error => this.showErrorModal(error));
  }

  private showErrorModal(error: HttpErrorResponse) {
    const alert = new BootstrapAlert(this.customErrorHandler.showError(error), 'alert-danger');
    alert.timeoutInMiliSeconds = AppConstants.ERROR_ALERT_TIMEOUT;
    this.bootstrapAlertService.alert(alert);
  }
}
