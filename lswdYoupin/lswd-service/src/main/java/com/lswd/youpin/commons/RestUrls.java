package com.lswd.youpin.commons;

/**
 * Created by liruilong on 15/8/1.
 */
public final class RestUrls {

	public static final String START_PROCESS_URL = "/service/runtime/process-instances";

	public static final String TASK_LIST_URL = "/service/runtime/tasks";

	public static final String TASK_ACTION_URL = "/service/runtime/tasks/{taskId}";

	public static final String GET_PROCESS_INSTANCE_DIAGRAM_URL = "/service/runtime/process-instances/{processInstanceId}/custom-diagram";

	public static final String PROCESS_DEFINITION_LIST_URL = "/service/repository/process-definitions";

	public static final String DEPLOY_RESOURCE_LIST_URL = "/service/repository/deployments/{deploymentId}/resources";

	public static final String DEPLOY_RESOURCE_CONTENT_URL = "/service/repository/deployments/{deploymentId}/resourcedata/{resourceId}";

	public static final String GET_PROCESS_DESTINITION_BY_ID_URL = "/service/repository/process-definitions/{processDefinitionId}";

	public static final String GET_PROCESS_INSTANCE = "/service/runtime/process-instances/{processInstanceId}";

	public static final String IDENTITY_LINKS_FOR_TASKS_URL = "/service/runtime/tasks/{taskId}/identitylinks";

	public static final String PUT_TASK_VARIABLES = "/service/runtime/tasks/{taskId}";

	public static final String GET_EXECUTION_VARIABLES = "/service/runtime/executions/{executionId}/variables/{variableName}";

	public static final String SEND_EMS_IDENTIFY_CODE = "sendEMSIdentifyCodeWs";

	public static final String MEN_SHI_LIST_COUNT = "menshiCountListWs";

	public static final String MEN_SHI_LIST = "menshiListWs";

	public static final String MEN_SHI_MAN_DETAIL = "menshiManDetailWs";

	public static final String ORDER_LIST = "orderListNewWs";

	public static final String ORDER_DETAIL = "orderDetailNewWs";

	public static final String ORDER_CREATE = "orderCreateNewWs";

	public static final String USER_REGISTER = "userRegisterNewWs";

	public static final String ORDER_STATUS_CHANGE = "orderStatusChangeNewWs";
}
