import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ContactNumberEditComponent } from './contact-number-edit.component';

describe('ContactNumberEditComponent', () => {
  let component: ContactNumberEditComponent;
  let fixture: ComponentFixture<ContactNumberEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ContactNumberEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ContactNumberEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
