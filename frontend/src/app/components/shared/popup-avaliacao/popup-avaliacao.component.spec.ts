import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PopupAvaliacaoComponent } from './popup-avaliacao.component';

describe('PopupAvaliacaoComponent', () => {
  let component: PopupAvaliacaoComponent;
  let fixture: ComponentFixture<PopupAvaliacaoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PopupAvaliacaoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PopupAvaliacaoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
