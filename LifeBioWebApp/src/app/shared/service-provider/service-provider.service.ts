import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class ServiceProviderService {

  public API = '//localhost:8084/lifeBioUI';
  public SERVICE_PROVIDER_API = this.API + '/dashboard/serviceprovider';
  private headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');

  constructor(private http: HttpClient) { }

  findAllServiceProviders(): Observable<any> {
      return this.http.get(this.SERVICE_PROVIDER_API + '/all', {headers: this.headers});
    }

  findServiceProvider(oid: Number) {
    return this.http.get(this.SERVICE_PROVIDER_API + '/' + oid, {headers: this.headers});
  }

  saveServiceProvider(serviceProvider: any): Observable<any> {
    let result: Observable<Object>;
    if (serviceProvider['href']) {
      result = this.http.put(serviceProvider.href, serviceProvider);
    } else {
      result = this.http.post(this.SERVICE_PROVIDER_API, serviceProvider);
    }
    return result;
  }

  removeServiceProvider(href: string) {
    return this.http.delete(href);
  }

}
