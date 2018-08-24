import { Injectable } from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {Router} from '@angular/router';
import {AppConstants} from '../app-constants';
import {HttpClient} from '@angular/common/http';
import {ContactDetails, ContactNumber, ContactType} from './ContactDetails';

@Injectable()
export class ContactDetailsService {
  contactDetailsOID: number;
  parentOID: number;
  parentObjectType: string;
  contactDetailsParent: object;

  constructor(private http: HttpClient) { }

  findContactDetails(oid: Number): Observable<ContactDetails> {
    const url = `${AppConstants.CONTACT_DETAILS_API}/${oid}`;
    return this.http.get<ContactDetails>(url, {headers: AppConstants.HEADERS});
  }

  changeContactDetails(contactDetails: ContactDetails): Observable<ContactDetails> {
    return this.http.put<ContactDetails>(AppConstants.CONTACT_DETAILS_API + '/' + contactDetails.oid, contactDetails,
      {headers: AppConstants.HEADERS});
  }

  addContactDetails(contactDetails: ContactDetails, parentOID: Number): Observable<ContactDetails> {
    return this.http.post<ContactDetails>(AppConstants.SERVICE_PROVIDER_API + '/' + parentOID + '/' + AppConstants.CONTACT_DETAILS_API,
      contactDetails, {headers: AppConstants.HEADERS})
      .map(response => {
        return response;
      })
      .catch((error: Response) => Observable.throw(error));
  }


  removeContactDetails(contactDetails: ContactDetails) {
    return this.http.delete(AppConstants.CONTACT_DETAILS_API + '/' + this.contactDetailsOID, {headers: AppConstants.HEADERS});
  }

  findContactTypes(): Observable<ContactType> {
    const url = `${AppConstants.CONTACT_TYPES_API}`;
    return this.http.get<ContactType>(url, {headers: AppConstants.HEADERS});
  }

  findAllContactNumbers(contactDetailsOID: Number): Observable<ContactNumber[]> {
    const url = `${AppConstants.CONTACT_NUMBER_API}/all/${contactDetailsOID}`;
    return this.http.get<ContactNumber[]>(url, {headers: AppConstants.HEADERS});
  }

  findContactNumber(oid: Number): Observable<ContactNumber> {
    const url = `${AppConstants.CONTACT_NUMBER_API}/${oid}`;
    return this.http.get<ContactNumber>(url, {headers: AppConstants.HEADERS});
  }

  changeContactNumber(contactNumber: ContactNumber): Observable<ContactNumber> {
    return this.http.put<ContactNumber>(AppConstants.CONTACT_NUMBER_API + '/' + contactNumber.oid, contactNumber,
      {headers: AppConstants.HEADERS});
  }

  addContactNumber(contactDetailsOID: Number, contactNumber: ContactNumber): Observable<ContactNumber> {
    return this.http.post<ContactNumber>(AppConstants.CONTACT_NUMBER_API + '/' + contactDetailsOID, contactNumber,
      {headers: AppConstants.HEADERS})
      .map(response => {
        return response;
      })
      .catch((error: Response) => Observable.throw(error));
  }


  removeContactNumber(contactNumber: ContactNumber) {
    return this.http.delete(AppConstants.CONTACT_NUMBER_API + '/' + this.contactDetailsOID + '/' + contactNumber.oid,
      {headers: AppConstants.HEADERS});
  }

  goToList(router: Router) {
    return router.navigate(['/contact-details', this.parentObjectType, this.parentOID, this.contactDetailsOID]);
  }
}
