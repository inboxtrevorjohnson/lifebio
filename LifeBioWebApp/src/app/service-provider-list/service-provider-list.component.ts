import { Component, OnInit } from '@angular/core';
import { ServiceProviderService } from '../shared/service-provider/service-provider.service';

@Component({
  selector: 'app-service-provider-list',
  templateUrl: './service-provider-list.component.html',
  styleUrls: ['./service-provider-list.component.css']
})
export class ServiceProviderListComponent implements OnInit {

  serviceProviders: Array<any>;


  constructor(private serviceProviderService: ServiceProviderService) { }

  ngOnInit() {
      this.serviceProviderService.findAllServiceProviders().subscribe(data => {
        this.serviceProviders = data;
      });
   }

}
