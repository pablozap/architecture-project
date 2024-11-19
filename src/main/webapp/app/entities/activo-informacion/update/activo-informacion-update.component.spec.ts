import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IGrupoActivos } from 'app/entities/grupo-activos/grupo-activos.model';
import { GrupoActivosService } from 'app/entities/grupo-activos/service/grupo-activos.service';
import { ActivoInformacionService } from '../service/activo-informacion.service';
import { IActivoInformacion } from '../activo-informacion.model';
import { ActivoInformacionFormService } from './activo-informacion-form.service';

import { ActivoInformacionUpdateComponent } from './activo-informacion-update.component';

describe('ActivoInformacion Management Update Component', () => {
  let comp: ActivoInformacionUpdateComponent;
  let fixture: ComponentFixture<ActivoInformacionUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let activoInformacionFormService: ActivoInformacionFormService;
  let activoInformacionService: ActivoInformacionService;
  let grupoActivosService: GrupoActivosService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ActivoInformacionUpdateComponent],
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
      .overrideTemplate(ActivoInformacionUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ActivoInformacionUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    activoInformacionFormService = TestBed.inject(ActivoInformacionFormService);
    activoInformacionService = TestBed.inject(ActivoInformacionService);
    grupoActivosService = TestBed.inject(GrupoActivosService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call GrupoActivos query and add missing value', () => {
      const activoInformacion: IActivoInformacion = { id: 456 };
      const grupoActivo: IGrupoActivos = { id: 10414 };
      activoInformacion.grupoActivo = grupoActivo;

      const grupoActivosCollection: IGrupoActivos[] = [{ id: 2962 }];
      jest.spyOn(grupoActivosService, 'query').mockReturnValue(of(new HttpResponse({ body: grupoActivosCollection })));
      const additionalGrupoActivos = [grupoActivo];
      const expectedCollection: IGrupoActivos[] = [...additionalGrupoActivos, ...grupoActivosCollection];
      jest.spyOn(grupoActivosService, 'addGrupoActivosToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ activoInformacion });
      comp.ngOnInit();

      expect(grupoActivosService.query).toHaveBeenCalled();
      expect(grupoActivosService.addGrupoActivosToCollectionIfMissing).toHaveBeenCalledWith(
        grupoActivosCollection,
        ...additionalGrupoActivos.map(expect.objectContaining),
      );
      expect(comp.grupoActivosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const activoInformacion: IActivoInformacion = { id: 456 };
      const grupoActivo: IGrupoActivos = { id: 30165 };
      activoInformacion.grupoActivo = grupoActivo;

      activatedRoute.data = of({ activoInformacion });
      comp.ngOnInit();

      expect(comp.grupoActivosSharedCollection).toContain(grupoActivo);
      expect(comp.activoInformacion).toEqual(activoInformacion);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IActivoInformacion>>();
      const activoInformacion = { id: 123 };
      jest.spyOn(activoInformacionFormService, 'getActivoInformacion').mockReturnValue(activoInformacion);
      jest.spyOn(activoInformacionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ activoInformacion });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: activoInformacion }));
      saveSubject.complete();

      // THEN
      expect(activoInformacionFormService.getActivoInformacion).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(activoInformacionService.update).toHaveBeenCalledWith(expect.objectContaining(activoInformacion));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IActivoInformacion>>();
      const activoInformacion = { id: 123 };
      jest.spyOn(activoInformacionFormService, 'getActivoInformacion').mockReturnValue({ id: null });
      jest.spyOn(activoInformacionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ activoInformacion: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: activoInformacion }));
      saveSubject.complete();

      // THEN
      expect(activoInformacionFormService.getActivoInformacion).toHaveBeenCalled();
      expect(activoInformacionService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IActivoInformacion>>();
      const activoInformacion = { id: 123 };
      jest.spyOn(activoInformacionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ activoInformacion });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(activoInformacionService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareGrupoActivos', () => {
      it('Should forward to grupoActivosService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(grupoActivosService, 'compareGrupoActivos');
        comp.compareGrupoActivos(entity, entity2);
        expect(grupoActivosService.compareGrupoActivos).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
