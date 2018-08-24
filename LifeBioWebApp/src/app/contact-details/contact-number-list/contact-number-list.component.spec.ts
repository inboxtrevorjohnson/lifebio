import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ContactNumberListComponent } from './contact-number-list.component';

describe('ContactNumberListComponent', () => {
  let component: ContactNumberListComponent;
  let fixture: ComponentFixture<ContactNumberListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ContactNumberListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ContactNumberListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
