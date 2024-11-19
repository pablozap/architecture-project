import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { ActivoInformacionDetailComponent } from './activo-informacion-detail.component';

describe('ActivoInformacion Management Detail Component', () => {
  let comp: ActivoInformacionDetailComponent;
  let fixture: ComponentFixture<ActivoInformacionDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ActivoInformacionDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./activo-informacion-detail.component').then(m => m.ActivoInformacionDetailComponent),
              resolve: { activoInformacion: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ActivoInformacionDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ActivoInformacionDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load activoInformacion on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ActivoInformacionDetailComponent);

      // THEN
      expect(instance.activoInformacion()).toEqual(expect.objectContaining({ id: 123 }));
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
