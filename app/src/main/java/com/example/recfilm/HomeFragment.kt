package com.example.recfilm

import android.os.Bundle
import android.view.Gravity
import android.view.Gravity.apply
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.GravityCompat.apply
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.Scene
import androidx.transition.Slide
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.merge_home_screen_content.*
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var filmsAdapter: FilmListRecyclerAdapter
    val filmsDataBase = listOf(
        Film(
            "Interstellar",
            R.drawable.interstellar,
            "Будущее Земли было пронизано бедствиями, голодом и засухой. Есть только один способ обеспечить выживание человечества: межзвездное путешествие. Недавно обнаруженная червоточина в дальних уголках нашей Солнечной системы позволяет команде астронавтов отправиться туда, где раньше не бывал ни один человек, на планете, которая может иметь подходящую среду для поддержания человеческой жизни"
        ),
        Film(
            "The batman",
            R.drawable.the_batman,
            "Бэтмен отправляется в преступный мир Готэм-сити, когда убийца-садист оставляет за собой след загадочных улик. По мере того, как улики начинают приближаться к дому, а масштаб планов преступника становится ясным, он должен наладить новые отношения, разоблачить преступника и восстановить справедливость. к злоупотреблению властью и коррупции, которые давно преследуют мегаполис."
        ),
        Film(
            "The social",
            R.drawable.the_social,
            "Осенним вечером 2003 года выпускник Гарварда и гений компьютерного программирования Марк Цукерберг садится за свой компьютер и с энтузиазмом начинает работать над новой идеей. В ярости ведения блога и программирования то, что начинается в его комнате в общежитии, вскоре становится глобальной социальной сетью и революцией в общении. Всего через шесть лет и 500 миллионов друзей Марк Цукерберг стал самым молодым миллиардером в истории… но для этого предпринимателя успех приводит как к личным, так и к юридическим осложнениям."
        ),
        Film(
            "Star vars",
            R.drawable.star_vars_poster,
            "Спустя 30 лет после поражения Дарта Вейдера и Империи Рей, мусорщик с планеты Джакку, находит дроида BB-8, который знает местонахождение давно потерянного Люка Скайуокера. Рей, а также штурмовик-изгой и два контрабандиста оказываются в эпицентре битвы между Сопротивлением и устрашающими легионами Первого Ордена."
        ),
        Film(
            "Spider man",
            R.drawable.spider_man,
            "Тайная личность Питера Паркера раскрывается всему миру. Отчаянно нуждаясь в помощи, Питер обращается к Доктору Стрэнджу, чтобы заставить мир забыть, что он Человек-Паук. Заклинание идет ужасно неправильно и разрушает мультивселенную, вызывая чудовищных злодеев, которые могут уничтожить мир."
        ),
        Film(
            "Ready player one",
            R.drawable.ready_player_one,
            "В 2045 году реальный мир стал суровым местом. Единственный раз, когда Уэйд Уоттс (Тай Шеридан) по-настоящему чувствует себя живым, это когда он сбегает в ОАЗИС, захватывающую виртуальную вселенную, где большинство людей проводит свои дни. В OASIS вы можете идти куда угодно, делать что угодно, быть кем угодно — единственными ограничениями являются ваше собственное воображение. ОАЗИС был создан блестящим и эксцентричным Джеймсом Холлидеем (Марк Райлэнс), который оставил свое огромное состояние и полный контроль над Оазисом победителю конкурса, состоящего из трех частей, который он задумал, чтобы найти достойного наследника. Когда Уэйд справится с первым испытанием в искажающей реальность охоте за сокровищами, он и его друзья — The High Five — попадут в фантастическую вселенную открытий и опасностей, чтобы спасти ОАЗИС."
        ),
        Film(
            "Queens gambit",
            R.drawable.queens_gambit,
            "Девятилетняя сирота Бет Хармон тихая, угрюмая и внешне ничем не примечательная. То есть, пока она не сыграет свою первую партию в шахматы. Ее чувства обостряются, мышление становится яснее, и впервые в жизни она чувствует, что полностью контролирует ситуацию. К шестнадцати годам она участвует в Открытом чемпионате США. Но по мере того, как Бет оттачивает свои навыки в профессиональной сфере, ставки становятся выше, ее изоляция становится все более пугающей, а мысль о побеге становится все более заманчивой. По книге Уолтера Тевиса."
        ),
        Film(
            "Inception",
            R.drawable.inception,
            "Дом Кобб — искусный вор, лучший в опасном искусстве добычи, он крадет ценные секреты из глубины подсознания во сне, когда разум наиболее уязвим. Редкие способности Кобба сделали его желанным игроком в этом предательском новом мире корпоративного шпионажа, но также сделали его международным беглецом и стоили ему всего, что он когда-либо любил. Теперь Коббу предлагают шанс на искупление. Одна последняя работа может вернуть ему жизнь, но только если он сможет совершить невозможное, начало. Вместо идеального ограбления Коббу и его команде специалистов приходится проворачивать обратное: их задача — не украсть идею, а внедрить ее. Если им это удастся, это может быть идеальное преступление. Но никакое тщательное планирование или опыт не могут подготовить команду к встрече с опасным врагом, который, кажется, предугадывает каждое их движение. Враг, которого мог предвидеть только Кобб."
        ),
        Film(
            "Anna inventing",
            R.drawable.anna_inventing,
            "Дом Кобб — искусный вор, лучший в опасном искусстве добычи, он крадет ценные секреты из глубины подсознания во сне, когда разум наиболее уязвим. Редкие способности Кобба сделали его желанным игроком в этом предательском новом мире корпоративного шпионажа, но также сделали его международным беглецом и стоили ему всего, что он когда-либо любил. Теперь Коббу предлагают шанс на искупление. Одна последняя работа может вернуть ему жизнь, но только если он сможет совершить невозможное, начало. Вместо идеального ограбления Коббу и его команде специалистов приходится проворачивать обратное: их задача — не украсть идею, а внедрить ее. Если им это удастся, это может быть идеальное преступление. Но никакое тщательное планирование или опыт не могут подготовить команду к встрече с опасным врагом, который, кажется, предугадывает каждое их движение. Враг, которого мог предвидеть только Кобб."
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Анимация появления главного экрана
        val scene = Scene.getSceneForLayout(home_fragment_root,R.layout.merge_home_screen_content,requireContext())
        val searchSlide = Slide(Gravity.TOP).addTarget(R.id.search_view)
        val recyclerSlide = Slide(Gravity.BOTTOM).addTarget(R.id.main_recycler)
        val customTransition = TransitionSet().apply{
            duration = 500
            addTransition(searchSlide)
            addTransition(recyclerSlide)
        }
        TransitionManager.go(scene,customTransition)

        main_recycler.apply {
            filmsAdapter =
                FilmListRecyclerAdapter(object : FilmListRecyclerAdapter.OnItemClickListener {
                    override fun click(film: Film) {
                        (requireActivity() as MainActivity).launchDetailsFragment(film)
                    }
                })
            adapter = filmsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            val decorator = TopSpacingItemDecoration(8)
            addItemDecoration(decorator)
        }

        filmsAdapter.addItems(filmsDataBase)

        //нажатие на поле "поиск" целиком
        search_view.setOnClickListener {
            search_view.isIconified = false
        }

        //Слушатель изменений введенного текста
        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            // Обработка нажатия "поиск" на клавиатуре
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            //Обработка кайдого изменения текста
            override fun onQueryTextChange(newText: String): Boolean {
                //Если ввод пуст вставляем всю БД в адаптер
                if (newText.isEmpty()) {
                    filmsAdapter.addItems(filmsDataBase)
                    return true
                }
                //Фильтруем список на подходящие сочетания
                val result = filmsDataBase.filter {
                    //Приводим запрос и ммя фильма к нижнему регистру
                    it.title.toLowerCase(Locale.getDefault()).contains(newText.toLowerCase(Locale.getDefault()))
                }
                //Добавляем в адаптер
                filmsAdapter.addItems(result)
                return true
            }
        })
    }
}