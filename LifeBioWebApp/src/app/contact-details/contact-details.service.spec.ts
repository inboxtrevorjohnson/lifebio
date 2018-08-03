import { TestBed, inject } from '@angular/core/testing';

import { ContactDetailsService } from '../shared/contact-details/contact-details.service';

describe('ContactDetailsService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ContactDetailsService]
    });
  });

  it('should be created', inject([ContactDetailsService], (service: ContactDetailsService) => {
    expect(service).toBeTruthy();
  }));
});
