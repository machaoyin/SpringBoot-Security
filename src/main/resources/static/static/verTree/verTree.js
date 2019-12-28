window.verTree = (function () {
    var tree = function (params) {
        if (!ie()) {
            alert("当前浏览器不支持verTree插件！");
            return false;
        }
        props;
        //加载css样式
        var path = this.getPath,
            common_css_href = path + "need/common.css?v=1.0&_=" + Math.random(),
            icon_css_href = path + "need/treeIcon.css?v=1.0&_=" + Math.random(),
            common_link = document.createElement("link"),
            icon_link = document.createElement("link");
        common_link.href = common_css_href;
        icon_link.href = icon_css_href;
        common_link.rel = icon_link.rel = "stylesheet";
        common_link.type = icon_link.type = "text/css";
        var link = document.getElementsByTagName("head")[0];
        link.appendChild(common_link);
        link.appendChild(icon_link);
        this.tree = document.querySelector(params.items);
        this.tree.classList.add("tree-" + params.type);
        this.data = params.data;
        this.type = params.type ? params.type : "data";
        this.option = params.option ? params.option : "";
        this.pk = params.parent ? params.parent : "parent";
        this.id = params.params ? params.params : "id";
        this.value = params.value ? params.value : "name";
        this.name = params.name ? params.name : this.id;
        this.defaults = params.defaults ? params.defaults : [];
        this.open_close = params.open_close ? params.open_close : "close";
        switch (this.type) {
            case "list":
                this.thead = (this.tree).querySelector('[data-tree-list="true"]');
                this.table_tree();
                break;
            case "form":
                this.items();
                break;
            default:
                this.items();
                break;
        }
    };
    tree.prototype = {
        getPath: function () {
            var jsPath = document.currentScript ? document.currentScript.src : function () {
                var js = document.scripts
                    , last = js.length - 1
                    , src;
                for (var i = last; i > 0; i--) {
                    if (js[i].readyState === 'interactive') {
                        src = js[i].src;
                        break;
                    }
                }
                return src || js[last].src;
            }();
            return jsPath.substring(0, jsPath.lastIndexOf('/') + 1);
        }(),
        items: function () {
            this.tree.classList.add("tree-form-box");
            this.tree_data(this.data, 0);
            if (this.type == "form") {
                this.checkInput();
            }
            this.tree_options_list();
        },
        tree_data: function (data, level, cl) {
            var ul = document.createElement("ul");
            // console.log(this.defaults);
            var f = level + 1;
            var icons = icon = "";
            for (var i in data) {
                var d = data[i];
                var i_className = "icon-plus";
                if (this.open_close != "open" && level>0) {
                    ul.classList.add("ver-tree-levels-hide");
                } else if(this.open_close == "open") {
                    i_className = "icon-minus";
                }
                if (d.children.length > 0) {
                    icon = "<i class='verTreeIcon " + i_className + " green tree-option'></i>";
                }
                var icons_class = "icon-check-box";
                var checkboxs = "";
                if (this.defaults.length && this.defaults.indexOf(d[this.id].toString()) >= 0) {
                    icons_class = "icon-check-box-cicre";
                    checkboxs = "checked='checked'";
                }
                if (this.type == "form") {
                    icons = "<span><i class='verTreeIcon " + icons_class + " blue ver-tree-check ver-tree-checks'></i><input type='checkbox' name='" + this.name + "[" + level + "][]' value='" + d[this.id] + "' style='display: none' " + checkboxs + "></span> "
                }
                var li = document.createElement("li");
                li.setAttribute("data-parent", d[this.pk]);
                li.setAttribute("data-id", d[this.id]);
                li.setAttribute("data-level", level);
                li.innerHTML = icon + icons + d[this.value];
                li.className = "ver-tree-levels ver-tree-level-" + level + " tree-parent-" + d[this.pk];

                ul.appendChild(li);
                if (cl) {
                    cl.appendChild(ul);
                } else {
                    this.tree.appendChild(ul);
                }
                if (d.children.length > 0) {
                    this.tree_data(d.children, f, li);
                }
            }
        },
        /*
         * 表格列表以树形菜单排列
         */
        table_tree: function () {
            var child = this.thead.querySelectorAll("[data-field]");
            var changes = this.thead.getAttribute("data-tree-changes");
            if (changes) {
                var cha = document.createElement("th");
                cha.innerHTML = "<i class='verTreeIcon icon-check-box blue ver-tree-check-all ver-tree-checks'></i>";
                this.thead.insertBefore(cha, this.thead.children[0])
            }
            // var chas = document.createElement("th");
            // this.thead.insertBefore(chas, this.thead.children[0]);
            // chas.style.width = "220";
            var tbody = document.createElement("tbody");
            var html = this.list_item(this.data, child, 0, changes, tbody, true);
            this.tree.appendChild(tbody);
            this.tree_options_list();
            this.checkInput();
        },
        /*
         * 表格数据处理
         */
        list_item: function (info, child, level, changes, objs, add, hide) {
            var l = "";
            if (level > 0) {
                l = "|";
                for (var j = 0; j < level; j++) {
                    l += "——"
                }
            }
            var sel = this;
            for (var i in info) {
                var data = info[i];
                var childs = "";
                var _h = "";
                if (level > 0) {
                    _h = l;
                }
                if (data.children.length) {
                    _h += "<i class='verTreeIcon icon-plus green tree-option'></i>";
                    childs = (data.children);
                }
                var cl = "";
                if (data[this.pk] > 0) {
                    cl = " tree-parent-" + data[this.pk];
                }
                var te = document.createElement("tr");
                var cl_te = "ver-tree-levels ver-tree-level-" + level + cl;
                if (hide) {
                    cl_te += " ver-tree-table-hide";
                }
                te.className = cl_te;
                te.setAttribute("data-parent", data[this.pk]);
                te.setAttribute("data-id", data[this.id]);
                if (changes) {
                    var change = document.createElement("td");
                    change.align = "center";
                    change.innerHTML = "<i class='verTreeIcon icon-check-box blue ver-tree-check ver-tree-checks'></i><input type='checkbox' name='" + this.name + "[]' value='" + data[this.id] + "' style='display: none'>";
                    te.appendChild(change);
                }
                // console.log(child);
                [].forEach.call(child, function (item,c) {
                    var file = item.getAttribute("data-field");
                    // html += '<td>' + data[file] + '</td>';
                    var tds = document.createElement("td");
                    var _tds = data[file];
                    if(c == 0){
                        tds.setAttribute("data-levels", level);
                        _tds = _h + " "+_tds
                    }
                    if (item.getAttribute("data-style")) {
                        tds.style = item.getAttribute("data-style");
                    }
                    tds.innerHTML = _tds;
                    te.appendChild(tds);
                });
                // var first_td = te.querySelector("td");
                // if(td.querySelector(".verTreeIcon"))
                // first_td.setAttribute("data-levels", level);
                // first_td.innerHTML = _h +" "+ first_td.innerHTML;
                if (add) {
                    objs.appendChild(te);
                    if (childs.length) {
                        var ls = level + 1;
                        this.list_item(childs, child, ls, changes, objs, true, true);
                    }
                } else {
                    sel.insertAfter(te, objs);
                }
            }
        },
        insertAfter: function (newElement, targentElement) {
            var parent = targentElement.parentNode;
            if (parent.lastChild == targentElement) {
                parent.appendChild(newElement);
            } else {
                parent.insertBefore(newElement, targentElement.nextSibling)
            }
        },
        /*
         * 收缩菜单控件
         */
        tree_options_list: function () {
            var options = this.tree.querySelectorAll(".tree-option");
            var _self = this;
            [].forEach.call(options, function (item) {
                item.onclick = function (e) {
                    if (_self.type == "list") {
                        var level = parseInt(this.parentElement.getAttribute("data-levels"));
                        var id = (this.parentElement.parentElement.getAttribute("data-id"));
                        var datas = this.parentElement.parentElement.parentElement.querySelectorAll("[data-parent='" + id + "']");
                        if (this.classList.contains("icon-plus")) {
                            this.classList.remove("icon-plus");
                            this.classList.add("icon-minus");
                            //显示子节点
                            [].forEach.call(datas, function (das) {
                                das.classList.remove("ver-tree-table-hide");
                            })
                        } else {
                            this.classList.add("icon-plus");
                            this.classList.remove("icon-minus");
                            _self.level_tops(id);

                        }
                    } else {
                        // var id = parseInt(this.parentElement.parentElement.getAttribute("data-level"));
                        var ul = (this.parentElement.querySelector("ul"));
                        if (this.classList.contains("icon-plus")) {
                            this.classList.remove("icon-plus");
                            this.classList.add("icon-minus");
                            ul.classList.remove("ver-tree-levels-hide");
                        } else {
                            this.classList.add("icon-plus");
                            this.classList.remove("icon-minus");
                            ul.classList.add("ver-tree-levels-hide");
                        }
                    }
                }
            });
        },
        /*
         * 全选&反选
         */
        checkInput: function () {
            var inputs = this.tree.querySelectorAll(".ver-tree-checks"),
                _self = this;
            [].forEach.call(inputs, function (item) {
                item.onclick = function () {
                    if (_self.type == "list") {
                        var ins = _self.tree.querySelectorAll(".ver-tree-check"),
                            all = _self.tree.querySelector(".ver-tree-check-all");
                        //判断是否是选中状态；
                        if (this.classList.contains("icon-check-box-cicre")) {
                            this.classList.add("icon-check-box");
                            this.classList.remove("icon-check-box-cicre");
                            //判断是全选还是单选
                            if (this.classList.contains("ver-tree-check-all")) {
                                [].forEach.call(ins, function (it) {
                                    // var parent = it.parentElement.parentElement;
                                    // if (!(parent.classList.contains("ver-tree-table-hide"))) {
                                    it.classList.add("icon-check-box");
                                    it.classList.remove("icon-check-box-cicre");
                                    it.parentElement.querySelector("input[type=checkbox]").checked = false;
                                    // }
                                });
                            } else {
                                this.parentElement.querySelector("input[type=checkbox]").checked = false;
                                all.classList.remove("icon-check-box-cicre");
                                all.classList.add("icon-check-box");

                            }
                        } else {
                            this.classList.remove("icon-check-box");
                            this.classList.add("icon-check-box-cicre");
                            //判断是全选还是单选
                            if (this.classList.contains("ver-tree-check-all")) {
                                [].forEach.call(ins, function (it) {
                                    var parent = it.parentElement.parentElement;
                                    if (!(parent.classList.contains("ver-tree-table-hide"))) {
                                        it.classList.add("icon-check-box-cicre");
                                        it.classList.remove("icon-check-box");
                                        it.parentElement.querySelector("input[type=checkbox]").checked = true;
                                    }
                                });
                            } else {
                                this.parentElement.querySelector("input[type=checkbox]").checked = true;

                                //判断是否全部选中
                                var name = this.parentElement.querySelector("input[type=checkbox]").name,
                                    check_len = _self.tree.querySelectorAll("input[name='" + name + "']:checked").length,
                                    length = ins.length;
                                if (check_len == length) {
                                    all.classList.remove("icon-check-box");
                                    all.classList.add("icon-check-box-cicre");
                                } else {
                                    all.classList.remove("icon-check-box-cicre");
                                    all.classList.add("icon-check-box");
                                }
                            }
                        }
                    } else if (_self.type == "form") {
                        var id = this.parentElement.parentElement.getAttribute("data-id"),
                            parent = this.parentElement.parentElement.getAttribute("data-parent"),
                            childrends = _self.tree.querySelectorAll(".tree-parent-" + id),
                            levels = parseInt(this.parentElement.parentElement.getAttribute("data-level")),
                            // tops = _self.tree.querySelectorAll(".ver-tree-level-0"),
                            p = _self.tree.querySelector("[data-id='" + parent + "']");
                        // l = 0;
                        //判断是否选中状态
                        if (this.classList.contains("icon-check-box-cicre")) {
                            this.classList.remove("icon-check-box-cicre");
                            this.classList.add("icon-check-box");
                            this.parentElement.querySelector("input[type=checkbox]").checked = false;
                            if (childrends.length > 0) {
                                [].forEach.call(childrends, function (items) {
                                    [].forEach.call(items.querySelectorAll(".ver-tree-checks"), function (it) {
                                        it.classList.remove("icon-check-box-cicre");
                                        it.classList.add("icon-check-box");
                                        it.parentElement.querySelector("input[type=checkbox]").checked = false;
                                    });
                                })
                            }
                        } else {
                            this.classList.remove("icon-check-box");
                            this.classList.add("icon-check-box-cicre");
                            this.parentElement.querySelector("input[type=checkbox]").checked = true;
                            if (levels > 0) {
                                var ls = levels - 1;
                                _self.tops(ls, id);
                            }

                            if (childrends.length > 0) {
                                [].forEach.call(childrends, function (items) {
                                    [].forEach.call(items.querySelectorAll(".ver-tree-checks"), function (it) {
                                        it.classList.remove("icon-check-box");
                                        it.classList.add("icon-check-box-cicre");
                                        it.parentElement.querySelector("input[type=checkbox]").checked = true;
                                    })
                                })
                            }
                        }
                    }
                }

            });
        },
        tops: function (levels, id) {
            var tops = document.querySelectorAll(".ver-tree-level-" + levels);

            if (tops.length > 0) {
                [].forEach.call(tops, function (item) {
                    if (item.querySelectorAll("[data-id='" + id + "']").length > 0) {
                        var icons = item.querySelector(".ver-tree-checks");
                        icons.classList.remove("icon-check-box");
                        icons.classList.add("icon-check-box-cicre");
                        icons.parentElement.querySelector("input[type=checkbox]").checkbox = true;
                    }
                });
                if (levels >= 0) {
                    this.tops((levels - 1), id);
                }
            } else {
                return false;
            }
        },
        level_tops: function (id) {
            var tops = document.querySelectorAll("[data-parent='" + id + "']");
            if (tops.length < 1) return false;
            for (var i = tops.length - 1; i >= 0; i--) {
                // cls
                var cl = tops[i],
                    ids = cl.getAttribute("data-id");
                cl.classList.add("ver-tree-table-hide");
                var verTreeIcon
                if (verTreeIcon = cl.querySelector(".verTreeIcon ")) {
                    verTreeIcon.classList.remove("icon-minus");
                    verTreeIcon.classList.add("icon-plus");
                }
                this.level_tops(ids);
            }
        }
    };
    //追加函数
    var props = function () {
        if (!Array.prototype.forEach) {
            Array.prototype.forEach = function (callback, thisArg) {
                var T, k;
                if (this == null) {
                    throw new TypeError(" this is null or not defined");
                }
                var O = Object(this);
                var len = O.length >>> 0; // Hack to convert O.length to a UInt32
                if ({}.toString.call(callback) != "[object Function]") {
                    throw new TypeError(callback + " is not a function");
                }
                if (thisArg) {
                    T = thisArg;
                }
                k = 0;
                while (k < len) {
                    var kValue;
                    if (k in O) {
                        kValue = O[k];
                        callback.call(T, kValue, k, O);
                    }
                    k++;
                }
            };
        }

        if (!("classList" in document.documentElement)) {
            Object.defineProperty(HTMLElement.prototype, 'classList', {
                get: function () {
                    var self = this;

                    function update(fn) {
                        return function (value) {
                            var classes = self.className.split(/\s+/g),
                                index = classes.indexOf(value);

                            fn(classes, index, value);
                            self.className = classes.join(" ");
                        }
                    }

                    return {
                        add: update(function (classes, index, value) {
                            if (!~index) classes.push(value);
                        }),

                        remove: update(function (classes, index) {
                            if (~index) classes.splice(index, 1);
                        }),

                        toggle: update(function (classes, index, value) {
                            if (~index)
                                classes.splice(index, 1);
                            else
                                classes.push(value);
                        }),

                        contains: function (value) {
                            return !!~self.className.split(/\s+/g).indexOf(value);
                        },

                        item: function (i) {
                            return self.className.split(/\s+/g)[i] || null;
                        }
                    };
                }
            });
        }
    }();

    //判断IE版本
    var ie = function () {
        var DEFAULT_VERSION = 8.0;
        var ua = navigator.userAgent.toLowerCase();
        var isIE = ua.indexOf("msie") > -1;
        var safariVersion;
        if (isIE) {
            safariVersion = ua.match(/msie ([\d.]+)/)[1];
        }
        if (safariVersion <= DEFAULT_VERSION) {
            return false
        }
        return true;
    };
    return tree;
})();