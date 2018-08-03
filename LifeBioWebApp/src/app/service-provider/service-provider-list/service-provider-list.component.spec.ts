import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiceProviderListComponent } from './service-provider-list.component';

describe('ServiceProviderListComponent', () => {
  let component: ServiceProviderListComponent;
  let fixture: ComponentFixture<ServiceProviderListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ServiceProviderListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ServiceProviderListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
