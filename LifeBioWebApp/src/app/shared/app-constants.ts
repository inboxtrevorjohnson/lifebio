import {HttpHeaders} from '@angular/common/http';

export class AppConstants {
  // API
  public static API = '//localhost:8084/lifeBioUI';
  public static SERVICE_PROVIDER_API = AppConstants.API + '/dashboard/serviceprovider';
  public static CONTACT_DETAILS_API = AppConstants.API + '/contactdetails';
  // Headers
  public static HEADERS = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
  // Time Information
  public static ERROR_ALERT_TIMEOUT = 5000;
  // Parent Object
  public static SERVICE_PROVIDER = 'Service Provider';
}
