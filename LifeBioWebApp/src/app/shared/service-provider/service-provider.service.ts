import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import {ServiceProvider} from './ServiceProvider';
import {Router} from '@angular/router';

@Injectable()
export class ServiceProviderService {

  private API = '//localhost:8084/lifeBioUI';
  private SERVICE_PROVIDER_API = this.API + '/dashboard/serviceprovider';
  private headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
  private serviceProvider: ServiceProvider;

  constructor(private http: HttpClient) { }

  findAllServiceProviders(): Observable<ServiceProvider[]> {
    return this.http.get<ServiceProvider[]>(this.SERVICE_PROVIDER_API + '/all', {headers: this.headers})
      .catch((error: any) => {
        return Observable.throw(error);
      });

  }

  findServiceProvider(oid: Number): Observable<ServiceProvider> {
    const url = `${this.SERVICE_PROVIDER_API}/${oid}`;
    return this.http.get<ServiceProvider>(url, {headers: this.headers});
  }

  changeServiceProvider(serviceProvider: ServiceProvider): Observable<ServiceProvider> {
    return this.http.put<ServiceProvider>(this.SERVICE_PROVIDER_API + '/' + serviceProvider.oid, serviceProvider,
      {headers: this.headers});
  }

  addServiceProvider(serviceProvider: ServiceProvider): Observable<ServiceProvider> {
    return this.http.post<ServiceProvider>(this.SERVICE_PROVIDER_API, serviceProvider, {headers: this.headers}).map(response => {
      console.log('ADDING SERVICE PROVIDER');
        return response;
    })
    .catch((error: Response) => Observable.throw(error));
  }


  removeServiceProvider(serviceProvider: ServiceProvider) {
    return this.http.delete(this.SERVICE_PROVIDER_API + '/' + serviceProvider.oid, {headers: this.headers});
  }

  goToList(router: Router) {
    return router.navigate(['/service-provider-list']);
  }

}
