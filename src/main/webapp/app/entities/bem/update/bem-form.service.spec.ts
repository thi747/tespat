import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../bem.test-samples';

import { BemFormService } from './bem-form.service';

describe('Bem Form Service', () => {
  let service: BemFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BemFormService);
  });

  describe('Service methods', () => {
    describe('createBemFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createBemFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            patrimonio: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
            numeroDeSerie: expect.any(Object),
            dataAquisicao: expect.any(Object),
            valorCompra: expect.any(Object),
            valorAtual: expect.any(Object),
            estado: expect.any(Object),
            status: expect.any(Object),
            observacoes: expect.any(Object),
            categoria: expect.any(Object),
            fornecedor: expect.any(Object),
            local: expect.any(Object),
          }),
        );
      });

      it('passing IBem should create a new form with FormGroup', () => {
        const formGroup = service.createBemFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            patrimonio: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
            numeroDeSerie: expect.any(Object),
            dataAquisicao: expect.any(Object),
            valorCompra: expect.any(Object),
            valorAtual: expect.any(Object),
            estado: expect.any(Object),
            status: expect.any(Object),
            observacoes: expect.any(Object),
            categoria: expect.any(Object),
            fornecedor: expect.any(Object),
            local: expect.any(Object),
          }),
        );
      });
    });

    describe('getBem', () => {
      it('should return NewBem for default Bem initial value', () => {
        const formGroup = service.createBemFormGroup(sampleWithNewData);

        const bem = service.getBem(formGroup) as any;

        expect(bem).toMatchObject(sampleWithNewData);
      });

      it('should return NewBem for empty Bem initial value', () => {
        const formGroup = service.createBemFormGroup();

        const bem = service.getBem(formGroup) as any;

        expect(bem).toMatchObject({});
      });

      it('should return IBem', () => {
        const formGroup = service.createBemFormGroup(sampleWithRequiredData);

        const bem = service.getBem(formGroup) as any;

        expect(bem).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IBem should not enable id FormControl', () => {
        const formGroup = service.createBemFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewBem should disable id FormControl', () => {
        const formGroup = service.createBemFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
