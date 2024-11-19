import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IRiesgo } from 'app/entities/riesgo/riesgo.model';
import { RiesgoService } from 'app/entities/riesgo/service/riesgo.service';
import { ControlesService } from '../service/controles.service';
import { IControles } from '../controles.model';
import { ControlesFormService } from './controles-form.service';

import { ControlesUpdateComponent } from './controles-update.component';

describe('Controles Management Update Component', () => {
  let comp: ControlesUpdateComponent;
  let fixture: ComponentFixture<ControlesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let controlesFormService: ControlesFormService;
  let controlesService: ControlesService;
  let riesgoService: RiesgoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ControlesUpdateComponent],
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
      .overrideTemplate(ControlesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ControlesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    controlesFormService = TestBed.inject(ControlesFormService);
    controlesService = TestBed.inject(ControlesService);
    riesgoService = TestBed.inject(RiesgoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Riesgo query and add missing value', () => {
      const controles: IControles = { id: 456 };
      const riesgos: IRiesgo[] = [{ id: 24765 }];
      controles.riesgos = riesgos;

      const riesgoCollection: IRiesgo[] = [{ id: 18107 }];
      jest.spyOn(riesgoService, 'query').mockReturnValue(of(new HttpResponse({ body: riesgoCollection })));
      const additionalRiesgos = [...riesgos];
      const expectedCollection: IRiesgo[] = [...additionalRiesgos, ...riesgoCollection];
      jest.spyOn(riesgoService, 'addRiesgoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ controles });
      comp.ngOnInit();

      expect(riesgoService.query).toHaveBeenCalled();
      expect(riesgoService.addRiesgoToCollectionIfMissing).toHaveBeenCalledWith(
        riesgoCollection,
        ...additionalRiesgos.map(expect.objectContaining),
      );
      expect(comp.riesgosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const controles: IControles = { id: 456 };
      const riesgo: IRiesgo = { id: 6065 };
      controles.riesgos = [riesgo];

      activatedRoute.data = of({ controles });
      comp.ngOnInit();

      expect(comp.riesgosSharedCollection).toContain(riesgo);
      expect(comp.controles).toEqual(controles);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IControles>>();
      const controles = { id: 123 };
      jest.spyOn(controlesFormService, 'getControles').mockReturnValue(controles);
      jest.spyOn(controlesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ controles });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: controles }));
      saveSubject.complete();

      // THEN
      expect(controlesFormService.getControles).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(controlesService.update).toHaveBeenCalledWith(expect.objectContaining(controles));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IControles>>();
      const controles = { id: 123 };
      jest.spyOn(controlesFormService, 'getControles').mockReturnValue({ id: null });
      jest.spyOn(controlesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ controles: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: controles }));
      saveSubject.complete();

      // THEN
      expect(controlesFormService.getControles).toHaveBeenCalled();
      expect(controlesService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IControles>>();
      const controles = { id: 123 };
      jest.spyOn(controlesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ controles });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(controlesService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareRiesgo', () => {
      it('Should forward to riesgoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(riesgoService, 'compareRiesgo');
        comp.compareRiesgo(entity, entity2);
        expect(riesgoService.compareRiesgo).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
