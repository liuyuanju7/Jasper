## 比较两个集合-获取新增、删除、更新的对象

#### 业务场景

在我们实际的业务场景中，经常会遇到这种类似的场景：需要对比两个集合，然后找到和老集合相比，新集合中 新增、删除、更新的对象，然后分别进行批量的数据库 新增、删除、更新操作或其他特殊处理，可能涉及的集合的对象不一致，我们需要重复写很多臃肿的业务代码，且不能复用

#### 问题抽象

- 集合间对象的比较：找到与目标集合中 新增、更新、删除的对象并进行分组筛选
- 业务对象的抽象：实现方法抽象，必须先对集合中的对象利用泛型进行抽象
- 比较方法：不同的业务，可能用来比较哪些对象是新增 或者 删除 更新的方法也是不一样的，包括`唯一标识比较`、`实体比较`
- 针对新增、删除、更新的对象要分别做特殊处理，或者转换成新的对象进行输出？

#### 可能方案

问题的核心是：如何基于不确定的泛型对象，针对不同的业务场景，利用不同的比较方法：类似于重写`equals()`方法一样，实现自定义的比较方法

因此想到了JAVA8中新增的两个特性：`Predicate`、`Function`，JAVA8中`lambda`的使用这里就不再赘述、不太了解的同学可以先熟悉一下，简单提下这两个函数的作用

##### Predicate

`Predicate<T> => boolean test(T t);`
 接受一个输入参数，返回布尔值  -- 用来解决 自定义比较规则 的问题？

```java
Predicate<String> predicate = (x) -> x.length() > 0;
predicate.test("String");
```

##### Function

`Function<T, R> => R apply(T t);`
 接受一个输入参数，返回一个结果 -- 用来解决 对对象进行自定义操作的 问题？

```java
Function<Integer, String> function1 = (x) -> "result: " + x;
function1.apply(6);
```

这两个函数的作用，看上去比较符合我们的需求，但是有一个问题就是：这两个函数都是只接收一个参数，但是我们上面的场景中，都是基于两个不同的对象进行比较，意思是，我们需要传入两个参数，然后来实现这些自定义的操作，JAVA8也提供了接收两个参数的函数`BiPredicate`、`BiFunction`

```java
BiPredicate<Integer, String> biPredicate = (age, name) -> (age >= 30 && name.equals("mars"));

BiFunction<Integer, Integer, Integer> biFunction = (a, b) -> a + b;
```

#### 工具方法

##### 获取新增的对象

```java
/**
 * 获取 source 集合 与 target 相比 新增的对象
 * @param source
 * @param target
 * @param keyPredicate 自定义比较 （一般比较主键即可
 * @return
 */
public static <S, T> List<S> getNewEntityList(List<S> source, List<T> target, BiPredicate keyPredicate){
    List<S> result = Lists.newArrayList();
    source.forEach(s -> {
        boolean notExist = target.stream().noneMatch(e -> keyPredicate.test(e, s));
        if (notExist) {
            // 目标集合中 不存在 则为 新增
            result.add(s);
        }
    });
    return result;
}
```

##### 获取新增的对象  并转换输出成 新对象

```java
/**
     * 获取 source 集合 与 target 相比 新增的对象, 并转换输出成 K 类型的集合
     * @param source
     * @param target
     * @param keyPredicate 自定义比较 （一般比较主键即可
     * @param func 自定义转换方法
     * @param <S> source 集合类型
     * @param <T> target 集合类型
     * @param <K> 转换输出的集合类型
     * @return
     */
    public static <S, T, K> List<K> convertNewEntityList(List<S> source, List<T> target, BiPredicate keyPredicate, Function<S, K> func){
        List<K> result = Lists.newArrayList();
        source.forEach(s -> {
            boolean notExist = target.stream().noneMatch(e -> keyPredicate.test(e, s));
            if (notExist) {
                // 目标集合中 不存在 则为 新增
                result.add(func.apply(s));
            }
        });
        return result;
    }
```

##### 获取删除的对象 

```java
/**
 * 获取 source 集合 与 target 相比 删除的对象 并转换输出成 K 类型的集合
 * @param source
 * @param target
 * @param keyPredicate 自定义比较 （一般比较主键即可
 * @return
 */
public static <S, T> List<T> getDelEntityList(List<S> source, List<T> target, BiPredicate keyPredicate){
    List<T> result = Lists.newArrayList();
    target.forEach(t -> {
        boolean notExist = source.stream().noneMatch(e -> keyPredicate.test(e, t));
        if (notExist) {
            result.add(t);
        }
    });
    return result;
}
```

##### 获取删除的对象 并转换输出成新的对象

```java
/**
     * 获取 source 集合 与 target 相比 删除的对象
     * @param source
     * @param target
     * @param keyPredicate 自定义比较 （一般比较主键即可
     * @return
     */
    public static <S, T, K> List<K> convertDelEntityList(List<S> source, List<T> target, BiPredicate keyPredicate, Function<T, K> func){
        List<K> result = Lists.newArrayList();
        target.forEach(t -> {
            boolean notExist = source.stream().noneMatch(e -> keyPredicate.test(e, t));
            if (notExist) {
                result.add(func.apply(t));
            }
        });
        return result;
    }
```

##### 获取更新的对象

- 判断更新需要判断两个：一个是根据唯一标示判读是否是同一个主键对象，再比较具体的实体属性判读是否有更新

```java
/**
 * 获取 source 集合 与 target 相比 source 中更新的对象
 * @param source
 * @param target
 * @param keyPredicate
 * @param entityPredicate
 * @return
 */
public static <S, T> List<S> getUpdateEntityList(List<S> source, List<T> target, BiPredicate keyPredicate, BiPredicate entityPredicate){
    List<S> result = Lists.newArrayList();
    source.forEach(s -> {
        Optional<T> t = target.stream().filter(e -> keyPredicate.test(e, s)).findFirst();
        if (t.isPresent() && !entityPredicate.test(s, t.get())) {
            // 目标集合中存在，但是属性不相等，则为 更新
            result.add(s);
        }
    });
    return result;
}
```

##### 获取更新对象 并输出生成新的对象

- 此处生成的对象为 根据原来两个集合中的对象 生成一个新类型的对象，所以用了`BiFunction`

```java
/**
 * 获取 source 集合 与 target 相比 source 中更新的对象, 并转换输出成 K 类型的集合
 * @param source
 * @param target
 * @param keyPredicate 自定义比较 一般为主键
 * @param entityPredicate 实体熟悉比较
 * @param func 对象转换方法
 * @param <S>
 * @param <T>
 * @param <K> 转换输出的集合类型
 * @return
 */
public static <S, T, K> List<K> convertUpdateEntityList(List<S> source, List<T> target,
                                                     BiPredicate keyPredicate, BiPredicate entityPredicate, BiFunction<S, T, K> func){
    List<K> result = Lists.newArrayList();
    source.forEach(s -> {
        Optional<T> t = target.stream().filter(e -> keyPredicate.test(e, s)).findFirst();
        if (t.isPresent() && !entityPredicate.test(s, t.get())) {
            // 目标集合中存在，但是属性不相等，则为 更新
            result.add(func.apply(s, t.get()));
        }
    });
    return result;
}
```

#### 测试实例

```java

	@Data
    @AllArgsConstructor
    static class Student {
        String id;
        String name;
        Integer age;
        String action;

    }
    public static void main(String[] args) {
        List<Student> source = Lists.newArrayList();
        source.add(new Student("1", "liusan", 12, ""));
        source.add(new Student("2", "liusan2", 18, ""));

        List<Student> target = Lists.newArrayList();
        target.add(new Student("1", "liusan", 14, ""));

        // 主键比较
        BiPredicate<Student, Student> keyPredicate = (s, t) -> s.id.equals(t.id);
        // 实体比较
        BiPredicate<Student, Student> entityPredicate = (s, t) -> s.age.equals(t.age) && s.name.equals(t.name);
        // 获取 新增对象
        List<Student> newEntityList = getNewEntityList(source, target, keyPredicate);
        // 获取 删除对象
        List<Student> delEntityList = getDelEntityList(target, source, keyPredicate);
        // 获取更新对象
        List<Student> updateEntityList = getUpdateEntityList(source, target, keyPredicate, entityPredicate);

        // 自定义 新增操作
        Function<Student, Student> addFunc = (stu) -> {
            stu.action = "新增";  // 可以进行具体的业务操作或数据转换
            return stu;
        };
        // 自定义 删除操作
        Function<Student, Student> delFunc = (stu) -> {
            stu.action = "删除"; // 可以进行具体的业务操作或数据转换
            return stu;
        };
        // 自定义 更新操作
        BiFunction<Student, Student, String> updateFunc = (stu1, stu2) -> stu1.name + stu2.name;

        // 获取 新增对象 并进行输出转换
        List<Student> newStudents = convertNewEntityList(source, target, keyPredicate, addFunc);
        // 获取 删除对象 并进行输出转换
        List<Student> delStudnets = convertDelEntityList(target, source, keyPredicate, delFunc);
        // 获取更新对象 并进行输出转换
        List<String> updateStudents = convertUpdateEntityList(source, target, keyPredicate, entityPredicate, updateFunc);
    }
```

