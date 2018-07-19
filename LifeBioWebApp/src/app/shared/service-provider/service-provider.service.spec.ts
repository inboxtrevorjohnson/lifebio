import { TestBed, inject } from '@angular/core/testing';

import { ServiceProviderService } from './service-provider.service';

describe('ServiceProviderService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ServiceProviderService]
    });
  });

  it('should be created', inject([ServiceProviderService], (service: ServiceProviderService) => {
    expect(service).toBeTruthy();
  }));
});
