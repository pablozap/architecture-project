import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { AmenazasDetailComponent } from './amenazas-detail.component';

describe('Amenazas Management Detail Component', () => {
  let comp: AmenazasDetailComponent;
  let fixture: ComponentFixture<AmenazasDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AmenazasDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./amenazas-detail.component').then(m => m.AmenazasDetailComponent),
              resolve: { amenazas: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(AmenazasDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AmenazasDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load amenazas on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', AmenazasDetailComponent);

      // THEN
      expect(instance.amenazas()).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
