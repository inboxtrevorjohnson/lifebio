import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiceProviderEditComponent } from './service-provider-edit.component';

describe('ServiceProviderEditComponent', () => {
  let component: ServiceProviderEditComponent;
  let fixture: ComponentFixture<ServiceProviderEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ServiceProviderEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ServiceProviderEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
