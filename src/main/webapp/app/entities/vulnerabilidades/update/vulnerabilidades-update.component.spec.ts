import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { VulnerabilidadesService } from '../service/vulnerabilidades.service';
import { IVulnerabilidades } from '../vulnerabilidades.model';
import { VulnerabilidadesFormService } from './vulnerabilidades-form.service';

import { VulnerabilidadesUpdateComponent } from './vulnerabilidades-update.component';

describe('Vulnerabilidades Management Update Component', () => {
  let comp: VulnerabilidadesUpdateComponent;
  let fixture: ComponentFixture<VulnerabilidadesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let vulnerabilidadesFormService: VulnerabilidadesFormService;
  let vulnerabilidadesService: VulnerabilidadesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [VulnerabilidadesUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(VulnerabilidadesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(VulnerabilidadesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    vulnerabilidadesFormService = TestBed.inject(VulnerabilidadesFormService);
    vulnerabilidadesService = TestBed.inject(VulnerabilidadesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const vulnerabilidades: IVulnerabilidades = { id: 456 };

      activatedRoute.data = of({ vulnerabilidades });
      comp.ngOnInit();

      expect(comp.vulnerabilidades).toEqual(vulnerabilidades);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVulnerabilidades>>();
      const vulnerabilidades = { id: 123 };
      jest.spyOn(vulnerabilidadesFormService, 'getVulnerabilidades').mockReturnValue(vulnerabilidades);
      jest.spyOn(vulnerabilidadesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ vulnerabilidades });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: vulnerabilidades }));
      saveSubject.complete();

      // THEN
      expect(vulnerabilidadesFormService.getVulnerabilidades).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(vulnerabilidadesService.update).toHaveBeenCalledWith(expect.objectContaining(vulnerabilidades));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVulnerabilidades>>();
      const vulnerabilidades = { id: 123 };
      jest.spyOn(vulnerabilidadesFormService, 'getVulnerabilidades').mockReturnValue({ id: null });
      jest.spyOn(vulnerabilidadesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ vulnerabilidades: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: vulnerabilidades }));
      saveSubject.complete();

      // THEN
      expect(vulnerabilidadesFormService.getVulnerabilidades).toHaveBeenCalled();
      expect(vulnerabilidadesService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVulnerabilidades>>();
      const vulnerabilidades = { id: 123 };
      jest.spyOn(vulnerabilidadesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ vulnerabilidades });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(vulnerabilidadesService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
