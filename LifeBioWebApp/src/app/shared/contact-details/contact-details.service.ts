import { Injectable } from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {Router} from '@angular/router';
import {AppConstants} from '../app-constants';
import {ServiceProvider} from '../service-provider/ServiceProvider';
import {HttpClient} from '@angular/common/http';
import {ContactDetails} from './ContactDetails';

@Injectable()
export class ContactDetailsService {
  private contactDetails: ContactDetails;

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
    return this.http.delete(AppConstants.CONTACT_DETAILS_API + '/' + this.contactDetails.oid, {headers: AppConstants.HEADERS});
  }

  goToList(router: Router) {
    return router.navigate(['/contact-details']);
  }

}
