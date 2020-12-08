package tju.cs.attendance.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import tju.cs.attendance.entity.Department;
import tju.cs.attendance.exception.Exceptions;
import tju.cs.attendance.service.DepartmentService;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Department)表控制层
 *
 * @author wuhaiying
 * @since 2020-12-02 18:35:06
 */
@RestController
@RequestMapping("department")
@Api(value = "department",description = "部门管理")
public class DepartmentController {
    /**
     * 服务对象
     */
    @Resource
    private DepartmentService departmentService;

//    /**
//     * 通过主键查询单条数据
//     *
//     * @param id 主键
//     * @return 单条数据
//     */
//    @GetMapping("selectOne")
//    public Department selectOne(Integer id) {
//        return this.departmentService.queryById(id);
//    }

    @PostMapping("add")
    @ApiOperation(value = "添加新部门")
    public ResponseEntity addDepartment(@ApiParam(value = "部门名称") @RequestParam(value = "name") String name){
        if(StringUtils.isEmpty(name)){
            throw new Exceptions.DataValidationFailedException("部门名称不能为空");
        }
        Department department = new Department();
        department.setName(name);
        Department department1 = departmentService.insert(department);
        return department1 == null ? ResponseEntity.ok(false) : ResponseEntity.ok(true);
    }

    @GetMapping("queryAll")
    @ApiOperation(value = "查询部门列表")
    public ResponseEntity<List<Department>> queryAll(){
        return ResponseEntity.ok(this.departmentService.queryAll());
    }

    @DeleteMapping("delete/{id}")
    @ApiOperation(value = "删除部门")
    public ResponseEntity deleteDepartment(@ApiParam(value = "id") @PathVariable(value = "id") Integer id){
        return ResponseEntity.ok(this.departmentService.deleteById(id));
    }

}