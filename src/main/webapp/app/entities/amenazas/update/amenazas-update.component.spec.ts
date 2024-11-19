import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { AmenazasService } from '../service/amenazas.service';
import { IAmenazas } from '../amenazas.model';
import { AmenazasFormService } from './amenazas-form.service';

import { AmenazasUpdateComponent } from './amenazas-update.component';

describe('Amenazas Management Update Component', () => {
  let comp: AmenazasUpdateComponent;
  let fixture: ComponentFixture<AmenazasUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let amenazasFormService: AmenazasFormService;
  let amenazasService: AmenazasService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AmenazasUpdateComponent],
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
      .overrideTemplate(AmenazasUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AmenazasUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    amenazasFormService = TestBed.inject(AmenazasFormService);
    amenazasService = TestBed.inject(AmenazasService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const amenazas: IAmenazas = { id: 456 };

      activatedRoute.data = of({ amenazas });
      comp.ngOnInit();

      expect(comp.amenazas).toEqual(amenazas);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAmenazas>>();
      const amenazas = { id: 123 };
      jest.spyOn(amenazasFormService, 'getAmenazas').mockReturnValue(amenazas);
      jest.spyOn(amenazasService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ amenazas });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: amenazas }));
      saveSubject.complete();

      // THEN
      expect(amenazasFormService.getAmenazas).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(amenazasService.update).toHaveBeenCalledWith(expect.objectContaining(amenazas));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAmenazas>>();
      const amenazas = { id: 123 };
      jest.spyOn(amenazasFormService, 'getAmenazas').mockReturnValue({ id: null });
      jest.spyOn(amenazasService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ amenazas: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: amenazas }));
      saveSubject.complete();

      // THEN
      expect(amenazasFormService.getAmenazas).toHaveBeenCalled();
      expect(amenazasService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAmenazas>>();
      const amenazas = { id: 123 };
      jest.spyOn(amenazasService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ amenazas });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(amenazasService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
