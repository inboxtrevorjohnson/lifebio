import {ErrorHandler, Injectable, Injector} from '@angular/core';
import {
  UNAUTHORIZED,
  BAD_REQUEST,
  FORBIDDEN,
  INTERNAL_SERVER_ERROR,
  CONFLICT,
  NOT_FOUND,
  METHOD_NOT_ALLOWED,
  UNSUPPORTED_MEDIA_TYPE, GATEWAY_TIMEOUT
} from 'http-status-codes';
import {Router} from '@angular/router';
import {HttpErrorResponse} from '@angular/common/http';

@Injectable()
export class CustomErrorHandler implements ErrorHandler {

  private router: Router;
  private message: string

  constructor(private injector: Injector) { }


  public handleError(error: HttpErrorResponse) {
    this.router = this.injector.get(Router);

    const httpErrorCode = error.error.statusCode;
    switch (httpErrorCode) {
      case UNAUTHORIZED: {
        this.router.navigateByUrl('/login');
        break;
      } case FORBIDDEN: {
        this.router.navigateByUrl('/unauthorized');
        break;
      } case BAD_REQUEST: {
        this.message = error.error.message;
        break;
      } case CONFLICT: {
        this.message = 'A value that should be unique isn\'t, try again!';
        break;
      } case INTERNAL_SERVER_ERROR: {
        this.message = 'An internal server error occurred, try again later!';
        break;
      } case GATEWAY_TIMEOUT: {
        this.message = error.error.message;
        break;
      } case METHOD_NOT_ALLOWED: {
        this.message = error.error.message;
        break;
      }  case NOT_FOUND: {
        this.message = error.error.message;
        break;
      } case UNSUPPORTED_MEDIA_TYPE: {
        this.message = error.error.message;
        break;
      } default:
        this.message = 'Internal server error, please try again later!';
    }
  }

  showError(error: HttpErrorResponse): string {
    this.handleError(error);
    return this.message;
  }
}
