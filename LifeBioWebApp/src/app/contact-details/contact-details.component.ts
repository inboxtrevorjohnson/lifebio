import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {CustomErrorHandler} from '../shared/custom-error-handler';
import {ContactDetailsService} from '../shared/contact-details/contact-details.service';
import {Subscription} from 'rxjs/Subscription';
import {ContactDetails} from '../shared/contact-details/ContactDetails';
import {AppConstants} from '../shared/app-constants';
import {ServiceProviderService} from '../shared/service-provider/service-provider.service';
import {ServiceProvider} from '../shared/service-provider/ServiceProvider';

@Component({
  selector: 'app-contact-details',
  templateUrl: './contact-details.component.html',
  styleUrls: ['./contact-details.component.css']
})
export class ContactDetailsComponent implements OnInit {

  subscription: Subscription;
  contactDetails: ContactDetails;
  title = 'Contact Details';
  parent: Object;

  constructor(private customErrorHandler: CustomErrorHandler,
              private route: ActivatedRoute,
              private router: Router,
              private contactDetailsService: ContactDetailsService,
              private serviceProviderService: ServiceProviderService) { }

  ngOnInit() {
    this.subscription = this.route.params.subscribe(params => {
      console.log('Params = ' + params);
      const parentObject = params['parentObject'];
      const parentOID = params['parentOID'];
      const oid = params['oid'];
      if (parentOID && oid && parentObject) {
        if(parentObject === AppConstants.SERVICE_PROVIDER) {
          this.parent = this.serviceProviderService.findServiceProvider(parentOID);
          this.title = 'Contact Details For Service Provider - ' + (this.parent as ServiceProvider).serviceProviderName;
        }
        // get parent details...
        this.contactDetailsService.findContactDetails(oid).subscribe((contactDetails: ContactDetails) => {
          if (contactDetails) {
            this.contactDetails = contactDetails;
          } else {
            console.log(`Contact details with for ' ${parent} ' with oid '${oid}' not found!`);
            this.contactDetailsService.goToList(this.router);
          }
        });
      } else {
        // Do something
      }
    });
  }

}
