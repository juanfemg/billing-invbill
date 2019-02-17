package co.com.juan.tcr.exceptions;

/**
 * @author Juan Felipe
 * 
 */
public class EntityException extends RuntimeException {

	private static final long serialVersionUID = -6090627865213545701L;
	public static final String ALL = "TODO(S) ";
	public static final String ENTCHILD = "Tablas relacionadas(menores)";
	public static final String FOREIGNDATA = "Datos de clases externos ";
	public static final String ENTITY_SUCCESFULLYSAVED = "Creado exitosamente";
	public static final String ENTITY_SUCCESFULLYDELETED = "Eliminado exitosamente";
	public static final String ENTITY_SUCCESFULLYMODIFIED = "Modificado exitosamente";
	public static final String ENTITY_WITHSAMEKEY = "Hay otra entidad con el mismo ID";
	public static final String ENTITY_NOENTITYTOUPDATE = "Ninguna entidad se encontro, con el id especificado";

	public EntityException() {
	}

	public EntityException(String exception) {
		super(exception);
	}

	public class NotValidFieldException extends EntityException {
		private static final long serialVersionUID = 1L;

		public NotValidFieldException(String info) {
			super("El valor para el campo: \"" + info + "\" no es valido");
		}
	}

	public class NullEntityExcepcion extends EntityException {
		private static final long serialVersionUID = 1L;

		public NullEntityExcepcion(String info) {
			super("La entidad" + info + " no puede estar vacia o nula");
		}
	}

	public class EmptyFieldException extends EntityException {
		private static final long serialVersionUID = 1L;

		public EmptyFieldException(String info) {
			super("El valor para el campo: \"" + info + "\" no puede estar vacio o nulo");
		}
	}

	public class NotValidFormatException extends EntityException {
		private static final long serialVersionUID = 1L;

		public NotValidFormatException(String info) {
			super("El tipo de dato o la longitud para el campo: \"" + info + "\" no es valido");
		}
	}

	public class SavingException extends EntityException {
		private static final long serialVersionUID = 1L;

		public SavingException(String info) {
			super("La entidad que intenta guardar tiene restricciones en algunos campos, porfavor antes de volver a intentarlo, compruebe los datos de la entidad, \""
					+ info + "\"");
		}
	}

	public class DeletingException extends EntityException {
		private static final long serialVersionUID = 1L;

		public DeletingException(String info) {
			super("La entidad que intenta borrar tiene informacion relacionada con otras entidades, por favor antes de volver a intentarlo, compruebe los datos de la entidad, \""
					+ info + "\"");
		}
	}

	public class UpdatingException extends EntityException {
		private static final long serialVersionUID = 1L;

		public UpdatingException(String info) {
			super("La entidad que intenta actualizar tiene restricciones en algunos campos, por favor antes de volver a intentarlo, compruebe los datos de la entidad, \""
					+ info + "\"");
		}
	}

	public class ForeignException extends EntityException {
		private static final long serialVersionUID = 1L;

		public ForeignException(String info) {
			super("No se encontro informacion relacionada con \"" + info + "\"");
		}
	}

	public class GettingException extends EntityException {
		private static final long serialVersionUID = 1L;

		public GettingException(String info) {
			super("Ocurrio una excepcion obteniendo " + info);
		}
	}

	public class FindingException extends EntityException {
		private static final long serialVersionUID = 1L;

		public FindingException(String info) {
			super("Ocurrio una excepcion tratando de encontrar " + info);
		}
	}
}
