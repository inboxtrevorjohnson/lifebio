import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { ActivatedRoute, Router } from '@angular/router';
import { ServiceProviderService } from '../shared/service-provider/service-provider.service';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-service-provider-edit',
  templateUrl: './service-provider-edit.component.html',
  styleUrls: ['./service-provider-edit.component.css']
})
export class ServiceProviderEditComponent implements OnInit, OnDestroy {
  serviceProvider: any = {};
  sub: Subscription;
  constructor(private route: ActivatedRoute,
              private router: Router,
              private serviceProviderService: ServiceProviderService) {
  }
  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
      const oid = params['oid'];
      if (oid) {
        this.serviceProviderService.findServiceProvider(oid).subscribe((serviceProvider: any) => {

          if (serviceProvider) {
            this.serviceProvider = serviceProvider;
            this.serviceProvider.href = serviceProvider._links.self.href;
          } else {
            console.log(`Service Provider with oid '${oid}' not found, returning to list`);
            this.gotoList();
          }
        });
      }
    });
  }
  ngOnDestroy() {
    this.sub.unsubscribe();
  }
  gotoList() {
    this.router.navigate(['/service-provider-list']);
  }
  save(form: NgForm) {
    this.serviceProviderService.saveServiceProvider(form).subscribe(result => {
      this.gotoList();
    }, error => console.error(error))
  }
  remove(href) {
    this.serviceProviderService.removeServiceProvider(href).subscribe(result => {
      this.gotoList();
    }, error => console.error(error))
  }
}
