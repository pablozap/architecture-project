import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { GrupoActivosDetailComponent } from './grupo-activos-detail.component';

describe('GrupoActivos Management Detail Component', () => {
  let comp: GrupoActivosDetailComponent;
  let fixture: ComponentFixture<GrupoActivosDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GrupoActivosDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./grupo-activos-detail.component').then(m => m.GrupoActivosDetailComponent),
              resolve: { grupoActivos: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(GrupoActivosDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GrupoActivosDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load grupoActivos on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', GrupoActivosDetailComponent);

      // THEN
      expect(instance.grupoActivos()).toEqual(expect.objectContaining({ id: 123 }));
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
