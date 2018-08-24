import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {CustomErrorHandler} from '../shared/custom-error-handler';
import {ContactDetailsService} from '../shared/contact-details/contact-details.service';
import {Subscription} from 'rxjs/Subscription';
import {AppConstants} from '../shared/app-constants';
import {ServiceProviderService} from '../shared/service-provider/service-provider.service';
import {ServiceProvider} from '../shared/service-provider/ServiceProvider';

@Component({
  selector: 'app-contact-details',
  templateUrl: './contact-details.component.html',
  styleUrls: ['./contact-details.component.css']
})

export class ContactDetailsComponent implements OnInit, OnDestroy {

  subscription: Subscription;
  title = 'Contact Details';

  constructor(private customErrorHandler: CustomErrorHandler,
              private route: ActivatedRoute,
              private router: Router,
              private contactDetailsService: ContactDetailsService,
              private serviceProviderService: ServiceProviderService) { }

  ngOnInit() {
    this.subscription = this.route.params
      .subscribe(params => {
      this.contactDetailsService.parentObjectType = params['parentObjectType'];
      this.contactDetailsService.parentOID = params['parentOID'];
      this.contactDetailsService.contactDetailsOID = params['contactDetailsOID'];
      if (this.contactDetailsService.parentOID && this.contactDetailsService.contactDetailsOID && this.contactDetailsService
        .parentObjectType) {
        if(this.contactDetailsService.parentObjectType === AppConstants.SERVICE_PROVIDER) {
          this.serviceProviderService.findServiceProvider(this.contactDetailsService.parentOID).subscribe(
            (serviceProvider: ServiceProvider) => {
            if (serviceProvider) {
              // Set Parent Values
              this.contactDetailsService.contactDetailsParent = serviceProvider;
              this.title = 'Contact Details For Service Provider - ' + (this.contactDetailsService.contactDetailsParent as ServiceProvider)
                .serviceProviderName;

              // Set Child Values
              // if(this.contactDetailsParent) {
              //  this.setContactDetails(this.contactDetailsOID);
              // }

            } else {
              console.log(`Service Provider with oid '${this.contactDetailsService.contactDetailsOID}' not found, returning to list`);
              this.serviceProviderService.goToList(this.router);
            }
          });

        }
      } else {
        this.contactDetailsService.goToList(this.router);
      }
    });
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }
}
