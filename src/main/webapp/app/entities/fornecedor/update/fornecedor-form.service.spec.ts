import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../fornecedor.test-samples';

import { FornecedorFormService } from './fornecedor-form.service';

describe('Fornecedor Form Service', () => {
  let service: FornecedorFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FornecedorFormService);
  });

  describe('Service methods', () => {
    describe('createFornecedorFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFornecedorFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            nome: expect.any(Object),
            descricao: expect.any(Object),
            cpfOuCnpj: expect.any(Object),
            email: expect.any(Object),
            telefone: expect.any(Object),
            endereco: expect.any(Object),
            cidade: expect.any(Object),
            estado: expect.any(Object),
          }),
        );
      });

      it('passing IFornecedor should create a new form with FormGroup', () => {
        const formGroup = service.createFornecedorFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            nome: expect.any(Object),
            descricao: expect.any(Object),
            cpfOuCnpj: expect.any(Object),
            email: expect.any(Object),
            telefone: expect.any(Object),
            endereco: expect.any(Object),
            cidade: expect.any(Object),
            estado: expect.any(Object),
          }),
        );
      });
    });

    describe('getFornecedor', () => {
      it('should return NewFornecedor for default Fornecedor initial value', () => {
        const formGroup = service.createFornecedorFormGroup(sampleWithNewData);

        const fornecedor = service.getFornecedor(formGroup) as any;

        expect(fornecedor).toMatchObject(sampleWithNewData);
      });

      it('should return NewFornecedor for empty Fornecedor initial value', () => {
        const formGroup = service.createFornecedorFormGroup();

        const fornecedor = service.getFornecedor(formGroup) as any;

        expect(fornecedor).toMatchObject({});
      });

      it('should return IFornecedor', () => {
        const formGroup = service.createFornecedorFormGroup(sampleWithRequiredData);

        const fornecedor = service.getFornecedor(formGroup) as any;

        expect(fornecedor).toMatchObject(sampleWithRequiredData);
      });
    });
  });
});
