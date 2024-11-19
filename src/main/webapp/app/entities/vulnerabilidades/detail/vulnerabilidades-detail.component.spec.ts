import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { VulnerabilidadesDetailComponent } from './vulnerabilidades-detail.component';

describe('Vulnerabilidades Management Detail Component', () => {
  let comp: VulnerabilidadesDetailComponent;
  let fixture: ComponentFixture<VulnerabilidadesDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VulnerabilidadesDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./vulnerabilidades-detail.component').then(m => m.VulnerabilidadesDetailComponent),
              resolve: { vulnerabilidades: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(VulnerabilidadesDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(VulnerabilidadesDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load vulnerabilidades on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', VulnerabilidadesDetailComponent);

      // THEN
      expect(instance.vulnerabilidades()).toEqual(expect.objectContaining({ id: 123 }));
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
