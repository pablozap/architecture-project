import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { RiesgoDetailComponent } from './riesgo-detail.component';

describe('Riesgo Management Detail Component', () => {
  let comp: RiesgoDetailComponent;
  let fixture: ComponentFixture<RiesgoDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RiesgoDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./riesgo-detail.component').then(m => m.RiesgoDetailComponent),
              resolve: { riesgo: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(RiesgoDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RiesgoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load riesgo on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', RiesgoDetailComponent);

      // THEN
      expect(instance.riesgo()).toEqual(expect.objectContaining({ id: 123 }));
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
