import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import {ServiceProvider} from './ServiceProvider';
import {Router} from '@angular/router';
import {AppConstants} from '../app-constants';

@Injectable()
export class ServiceProviderService {
  private serviceProvider: ServiceProvider;

  constructor(private http: HttpClient) { }

  findAllServiceProviders(): Observable<ServiceProvider[]> {
    return this.http.get<ServiceProvider[]>(AppConstants.SERVICE_PROVIDER_API + '/all', {headers: AppConstants.HEADERS})
      .catch((error: any) => {
        return Observable.throw(error);
      });

  }

  findServiceProvider(oid: Number): Observable<ServiceProvider> {
    const url = `${AppConstants.SERVICE_PROVIDER_API}/${oid}`;
    return this.http.get<ServiceProvider>(url, {headers: AppConstants.HEADERS});
  }

  changeServiceProvider(serviceProvider: ServiceProvider): Observable<ServiceProvider> {
    return this.http.put<ServiceProvider>(AppConstants.SERVICE_PROVIDER_API + '/' + serviceProvider.oid, serviceProvider,
      {headers: AppConstants.HEADERS});
  }

  addServiceProvider(serviceProvider: ServiceProvider): Observable<ServiceProvider> {
    return this.http.post<ServiceProvider>(AppConstants.SERVICE_PROVIDER_API, serviceProvider, {headers: AppConstants.HEADERS})
      .map(response => {
        return response;
    })
    .catch((error: Response) => Observable.throw(error));
  }


  removeServiceProvider(serviceProvider: ServiceProvider) {
    return this.http.delete(AppConstants.SERVICE_PROVIDER_API + '/' + serviceProvider.oid, {headers: AppConstants.HEADERS});
  }

  goToList(router: Router) {
    return router.navigate(['/service-provider-list']);
  }

}
